package Server;

import common.Post;
import common.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class Server {

    public static final int Port = 8082;
    public static Map<String, User> allUsers;
    public static Vector<Post> allPosts;

    public static void main(String[] args) {
        DataBase.getInstance().initializeServer();
        
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            Socket newSocket = null;
            try {
                assert serverSocket != null;
                newSocket = serverSocket.accept();
                ClientHandler newClientHandler = new ClientHandler(newSocket);
                new Thread(newClientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
