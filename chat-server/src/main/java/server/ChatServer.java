package server;

import java.io.IOException;

// Para criação da vars de instancia do servidor
public class ChatServer {

    public static void main(String[] args) throws IOException {
        ChatThreaded ct = new ChatThreaded();
        ct.daemonChat();
    }
}
