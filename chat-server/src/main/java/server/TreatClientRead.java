package server;


import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import java.net.Socket;
import protos.Protos.*;


public class TreatClientRead implements Runnable
{
    private Socket cs;
    private ChatThreaded server;
    private CodedInputStream cis;
    private CodedOutputStream cos;
    private Client client;
    private boolean statusFlag;
    
    TreatClientRead(Socket cs, ChatThreaded server) {
        this.cs = cs;
        this.server = server;
        this.cis = null;
        this.cos = null;
        this.client = null;
        this.statusFlag = false;
    }

    private RegisterMessage createRegisterMessage(RegisterMessage.Type type,
                                                  String value){
        return RegisterMessage.newBuilder()
                .setSender(RegisterMessage.Sender.REPLY)
                .setType(type)
                .setStage(RegisterMessage.Stage.PASSWORD)
                .setValue(value)
                .build();
    }

    private boolean register(RegisterMessage.Stage stage, String value){

        if(stage.equals(RegisterMessage.Stage.USERNAME)){
            if(!this.server.mapUsers.containsKey(value)){
                this.statusFlag = true;
                client = new Client(this.cs);
                client.setUsername(value);
            }
        }

        if(stage.equals(RegisterMessage.Stage.PASSWORD)) {
            if(statusFlag) {
                client.setPassword(value);
                client.setMessagePointer(server.logs.getPosition());
                server.mapUsers.put(client.getUsername(), client);
                return true;
            }
        }

        return false;
    }

    private boolean login(RegisterMessage.Stage stage, String value){
        if(stage.equals(RegisterMessage.Stage.USERNAME)){
            client = server.mapUsers.get(value);
            if(client == null) return false;
            else{
                statusFlag = true;
            }
        }

        if(stage.equals(RegisterMessage.Stage.PASSWORD)) {
            if(statusFlag) {
                if(client.getPassword().equals(value)) {
                    client.setSocket(this.cs);
                    client.setMessagePointer(server.logs.deleted);
                    server.mapUsers.get(client.getUsername()).setMessagePointer(server.logs.deleted);
                    server.mapUsers.get(client.getUsername()).setSocket(this.cs);
                    return true;
                }
                else {
                    client = null;
                    statusFlag = false;
                    return  false;
                }
            }
        }

        return false;
    }

    private void treatUser(){

        boolean finished = false;

        try {
            int len;

            while (true){
                len = cis.readRawLittleEndian32();
                byte[] read = cis.readRawBytes(len);
                RegisterMessage b = RegisterMessage.parseFrom(read);

                if(b.getType().equals(RegisterMessage.Type.REGISTER)){
                    finished = register(b.getStage(), b.getValue());
                }

                if(b.getType().equals(RegisterMessage.Type.AUTH)){
                    finished = login(b.getStage(), b.getValue());
                }

                if(b.getStage().equals(RegisterMessage.Stage.PASSWORD)) {
                    RegisterMessage response = createRegisterMessage(
                            b.getType(),
                            String.valueOf(finished));

                    byte[] ba = response.toByteArray();

                    cos.writeFixed32NoTag(ba.length);
                    cos.writeRawBytes(ba);
                    cos.flush();

                    if(finished){
                        return;
                    }
                }
            }

        }catch (Exception e){e.printStackTrace();}

    }


    private void read(){
        try {
            int len;
            byte[] ba;

            while(true){
                len = cis.readRawLittleEndian32();
                ba = cis.readRawBytes(len);

                this.server.logs.add(ba);
            }
        }
        catch (Exception e){}
    }
    
    public void run() {

        try {
            cis = CodedInputStream.newInstance(this.cs.getInputStream());
            cos = CodedOutputStream.newInstance(this.cs.getOutputStream());

        }catch (Exception e){}

        try {
            System.out.println("New Connection Received");

            treatUser();

            Thread tr = new Thread( new TreatClientWrite(client, server) );
            tr.start();

            read();

            System.out.println("Shutdown Output");
            cs.shutdownOutput();
            cs.close();
            System.out.println("Connection Closed");
        }catch (Exception e){}
        
    }
}