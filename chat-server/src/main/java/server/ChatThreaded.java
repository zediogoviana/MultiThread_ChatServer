package server;


import java.io.*;
import java.net.*;

import java.util.HashMap;
import java.util.Map;

class ChatThreaded {

    MessageBuffer logs;
    Map<String, Client> mapUsers;

    public ChatThreaded() {
        this.mapUsers = new HashMap<>();
        this.logs = new MessageBuffer(this.mapUsers);
    }

    public void daemonChat() throws IOException {
        ServerSocket ss = new ServerSocket(9999);
        Thread gc = new Thread( new GarbageCollector(this) );
        gc.start();

        Socket cs;

        while (true) {
            cs = ss.accept();
            //this.mapCounter.put(cs,logs.getPosition());
            Thread t = new Thread( new TreatClientRead(cs, this) );

            t.start();


        }
    }
}