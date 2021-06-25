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
                Command command=(Command) request.get("command");
                switch (command) {
                    case SIGN_IN -> answer = API.SignIn(request);
                    case SIGN_UP -> answer = API.SignUp(request);
                    case UNIQUE_USER -> answer = API.isExistingUsername(request);
                    case POSTING -> answer = API.Posting(request);
                    case GET_POSTS -> answer = API.getPosts(request);
                    case GET_MY_POSTS -> answer = API.getMyPosts(request);
                    case GET_USERS -> answer = API.getUsers(request);
                    case EDIT_PROFILE -> answer = API.EditProfile(request);
                    case LOG_OUT -> answer = API.Logout(request);
                    case LIKE -> answer = API.Like(request);
                    case UNLIKE -> answer = API.Unlike(request);
                    case LIKE_REPOST_COMMENT_NUMBERS -> answer = API.LikeRepostComment_Numbers(request);
                    case LIKE_MEMBERS -> answer = API.getLikedMembers(request);
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
