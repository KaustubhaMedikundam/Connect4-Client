import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Client extends Thread {


//    GameButton buttonBoard = new GameButton();

    GameButton[][] buttonBoard = new GameButton[6][7];
    int[][] board = new int[6][7];
    boolean win = false;
    String msg;
    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;

    private Consumer<Serializable> callback;

    CFourInfo game = new CFourInfo();
    public void updateClients(CFourInfo message) {
        game = message;
//
        //THIS IS WHERE WE WILL KEEP TRACK OF THE CFOURINFOUPDATES
    }
    Client(Consumer<Serializable> call){
        callback = call;
    }
    public void run() {
        try {
            socketClient= new Socket("127.0.0.1", 5555);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);

        }
        catch(Exception e) {}

        while(true) {
            try {
                CFourInfo message = (CFourInfo) in.readObject();
                callback.accept((Serializable) message);
//                updateClients(message);
            }
            catch(Exception e) {}
        }

    }

    public void send(CFourInfo data) {
        try {
            out.writeObject(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
