package client;

import java.io.*;
import java.net.*;
import java.util.Properties;

import client.menus.*;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import protos.Protos.*;


/**
 * Cliente
 *
 */

public class App {


    private Socket cs;    
    private String username;
    private boolean logged;

    private BufferedReader sin;
    private CodedOutputStream cos;
    private CodedInputStream cis;
    private Init initial;
    private static Properties prop;

    public App() throws IOException{

        this.initial = new Init();
        this.cs = this.connect();
        this.sin = new BufferedReader( new InputStreamReader(System.in));
        this.cis = CodedInputStream.newInstance(cs.getInputStream());
        this.cos = CodedOutputStream.newInstance(cs.getOutputStream());
        this.logged = false;

    }

    private RegisterMessage createRegisterMessage(RegisterMessage.Type type,
                                                  RegisterMessage.Stage stage,
                                                  String value){
        return RegisterMessage.newBuilder()
                .setSender(RegisterMessage.Sender.REQUEST)
                .setType(type)
                .setStage(stage)
                .setValue(value)
                .build();
    }

    private Socket connect() throws IOException {

        boolean connected = false;
        Socket cs = null;

        while (!connected) {
            try {
                cs = new Socket(prop.getProperty("server"), Integer.parseInt(prop.getProperty("port")));
                connected = true;
            } catch (ConnectException e) {
                this.initial.printCannot();
            }
        }
        return cs;
    }


    private void initialize(String s) throws IOException{

        if(s.equalsIgnoreCase("Q")){
            System.out.println("Shutting Down");
            this.cs.shutdownOutput();
            this.cs.shutdownInput();
            System.exit(0);
        }
        else{

            String username = this.initial.printLoginUsername();
            String password = this.initial.printLoginPassword();

            this.username = username;
            RegisterMessage messUser = null;
            RegisterMessage messPass = null;

            if(s.equalsIgnoreCase("L")){
                 messUser = createRegisterMessage(
                         RegisterMessage.Type.AUTH,
                        RegisterMessage.Stage.USERNAME,
                        username);

                 messPass = createRegisterMessage(
                         RegisterMessage.Type.AUTH,
                        RegisterMessage.Stage.PASSWORD,
                        password);

            }
            if(s.equalsIgnoreCase("R")){
                messUser = createRegisterMessage(
                        RegisterMessage.Type.REGISTER,
                        RegisterMessage.Stage.USERNAME,
                        username);

                messPass = createRegisterMessage(
                        RegisterMessage.Type.REGISTER,
                        RegisterMessage.Stage.PASSWORD,
                        password);
            }

            byte[] ba1 = messUser.toByteArray();
            byte[] ba2 = messPass.toByteArray();

            cos.writeFixed32NoTag(ba1.length);
            cos.writeRawBytes(ba1);
            cos.flush();

            cos.writeFixed32NoTag(ba2.length);
            cos.writeRawBytes(ba2);
            cos.flush();
        }

    }

    private String readInitialize() throws IOException{

        String s = null;
        int len = cis.readRawLittleEndian32();
        byte[] read = cis.readRawBytes(len);
        RegisterMessage b = RegisterMessage.parseFrom(read);

        if(b.getValue().equals("true")) this.logged = true;
        else {
            if(b.getType().equals(RegisterMessage.Type.AUTH)){
                s = this.initial.printInitError();
            }
            else s = this.initial.printUsedUsername();
        }

        return s;
    }


    private void readFromServer() throws IOException{

        int len;
        byte[] ba;

        while(true){
            len = cis.readRawLittleEndian32();
            ba = cis.readRawBytes(len);

            Message mess = Message.parseFrom(ba);

            System.out.println(mess.getSender() + ": " + mess.getMessage());
        }
    }


    public static void main( String[] args ) throws IOException{

        prop = new Properties();
        InputStream input = null;

        try {
            String filename = "config.properties";
            input = App.class.getClassLoader().getResourceAsStream(filename);
            if(input == null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }
            //load a properties file from class path, inside static method
            prop.load(input);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Server is Down");
        }

        App chat = new App();

        try {

            String s = chat.initial.printInit();
            while(!chat.logged){

                chat.initialize(s);
                s = chat.readInitialize();
            }

            System.out.print("\033[H\033[2J");  //CLEAR;

            Thread t = new Thread( new ClientWrite(chat.cs, chat.username) );
            t.start();

            chat.readFromServer();
        } catch (Exception e){
            System.out.println("Server is Down");
        }

        System.out.println("Shutting Down");
        chat.cs.shutdownOutput();
        chat.cs.shutdownInput();
        chat.cs.close();
    }
}

