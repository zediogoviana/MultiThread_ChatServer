package server;

public class TreatClientWrite implements Runnable
{
    private Client client;
    private ChatThreaded server;
    
    TreatClientWrite(Client client, ChatThreaded server) {
        this.client = client;
        this.server = server;
    }
    
    public void run() {
        
        try {

            server.logs.writeloop(client.getUsername());
            
        } catch ( Exception e) {
            e.printStackTrace();
        }
        
    }
}