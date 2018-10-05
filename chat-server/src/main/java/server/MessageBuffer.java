package server;

import com.google.protobuf.CodedOutputStream;
import protos.Protos.Message;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class MessageBuffer {

    Vector<byte[]> log = new Vector<>();
    Lock l = new ReentrantLock();
    private Map<String, Client> mapUsers;
    private Condition condition = this.l.newCondition();
    int deleted = 0;

    public MessageBuffer(Map<String, Client> mapSc){
        this.mapUsers = mapSc;
    }

    public void add(byte[] s) {
        l.lock();
        log.add(s);
        condition.signalAll();
        l.unlock();
    }

    public void writeloop(String username) {
        Client c = mapUsers.get(username);
        byte[] s;
        CodedOutputStream cos = null;

        try {
            cos = CodedOutputStream.newInstance(c.getSocket().getOutputStream());

            while (true) {
                l.lock();
                while ((c.getMessagePointer()-deleted) >= log.size()){
                    condition.await();
                }


                if(!c.getSocket().isClosed()) {

                    s = log.elementAt(c.getMessagePointer() - deleted);

                    mapUsers.get(c.getUsername()).incrementPointer();

                    l.unlock();

                    Message m = Message.parseFrom(s);

                    if(!m.getSender().equals(username)){
                        cos.writeFixed32NoTag(s.length);
                        cos.writeRawBytes(s);
                        cos.flush();
                    }
                }
                else{
                    l.unlock();
                }
            }

        } catch (Exception e) {}

    }


    public Integer getPosition(){
        l.lock();
        int i = log.size() + deleted;
        l.unlock();
        return i;
    }
}