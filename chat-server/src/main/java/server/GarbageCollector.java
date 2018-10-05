package server;

public class GarbageCollector implements Runnable {

    ChatThreaded server;

    GarbageCollector(ChatThreaded server){
        this.server = server;
    }

    public void run() {
        int min = Integer.MAX_VALUE;
        int counter = 0;

        try {

            while (true) {
                Thread.sleep(10000);
                server.logs.l.lock();
                for (Client c : server.mapUsers.values()) {
                    if (c.getMessagePointer() < min)
                        min = c.getMessagePointer();
                }

                if(min != Integer.MAX_VALUE) {
                    for (; counter < min; counter++) {
                        server.logs.log.remove(0);
                        System.out.println("removed: " + counter);
                    }

                    server.logs.deleted = counter;
                }
                server.logs.l.unlock();
                min = Integer.MAX_VALUE;
            }

        }catch (Exception e){e.printStackTrace();}
    }
}
