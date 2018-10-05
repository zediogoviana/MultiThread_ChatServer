package client;

import com.google.protobuf.CodedOutputStream;
import protos.Protos.Message;

import java.net.*;


public class ClientWrite implements Runnable {

    private Socket cs;
    private String username;

    ClientWrite(Socket cs, String username){
        this.cs = cs;
        this.username = username;
    }

    Message createMessage(String sender, String message ){
        return Message.newBuilder()
                .setSender(sender)
                .setMessage(message)
                .build();
    }

    public void run(){

        try{

            CodedOutputStream cos = CodedOutputStream.newInstance(cs.getOutputStream());

            while(true){

                String s1 = System.console().readLine();

                if (s1 == null)
                    break;

                Message mess = createMessage(this.username, s1);

                byte[] ba1 = mess.toByteArray();

                cos.writeFixed32NoTag(ba1.length);
                cos.writeRawBytes(ba1);
                cos.flush();
            }
        
        } catch(Exception e){
            System.out.println("Shutting Down");
            //cs.shutdownOutput();
        }
    }

}