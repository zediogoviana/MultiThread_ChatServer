package server;

import java.net.Socket;

public class Client {

    private String username;
    private String password;
    private Socket cs;
    private int messagePointer;

    public Client(String username, String password, Socket cs){
        this.username = username;
        this.password = password;
        this.cs = cs;
        this.messagePointer = 0;
    }

    public Client( Socket cs){
        this.username = "";
        this.password = "";
        this.cs = cs;
        this.messagePointer = 0;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public Socket getSocket(){
        return this.cs;
    }

    public int getMessagePointer(){
        return this.messagePointer;
    }

    public void incrementPointer(){
        this.messagePointer++;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setSocket(Socket cs){
        this.cs = cs;
    }

    public void setMessagePointer(int pointer){
        this.messagePointer = pointer;
    }

}
