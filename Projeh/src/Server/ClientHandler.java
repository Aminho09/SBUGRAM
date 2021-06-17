package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket userSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    public boolean isOnlineClient = true;

    public ClientHandler(Socket socket) {
        userSocket = socket;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
