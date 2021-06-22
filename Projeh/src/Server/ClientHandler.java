package Server;

import common.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

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
        while(true){
            Map<String,Object> request;
            try{
                request = (Map<String,Object>) inputStream.readObject();
                Map<String,Object> answer=null;
                Command command=(Command) request.get("request");
                switch(command){
                    case SIGN_IN:
                        answer = API.SignIn(request);
                        break;
//                    case newUsername:
//                        answer = API.isUsernameValid(request);
//                        break;
                    case SIGN_UP:
                        answer = API.SignUp(request);
                        break;
//                    case addPost:
//                        answer = API.addPost(request);
//                        break;
//                    case getPosts:
//                        answer = API.getPosts(request);
//                        break;
//                    case getMyPosts:
//                        answer = ServerAPI.getMyPosts(request);
//                        break;
//                    case getUsers:
//                        answer = ServerAPI.getUsers(request);
//                        break;
                    /*case TRASH_MAIL:
                        answer = API.trashMail(income);
                        break;
                    case READ_MAIL:
                        answer = API.readMail(income);
                        break;
*/
                }
                outputStream.writeObject(answer);
                outputStream.flush();
            }
            catch(ClassCastException | ClassNotFoundException e){
                e.printStackTrace();
            } catch(IOException e){
                break;
            }

        }
        try{
            inputStream.close();
            outputStream.close();
            userSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
