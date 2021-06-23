package Client;

import common.Post;
import common.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientMain extends Application {

    public static Post currentPost=new Post();
    public static User currentUser=new User();
    public static List<Post> allPosts=new CopyOnWriteArrayList<>();
    public static Map<String, User> users=new ConcurrentHashMap<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.initStage(primaryStage);
        PageLoader.load("LoginPage");
    }

    public static void update(){
        if(API.getAllPosts(ClientMain.currentUser) != null){
            allPosts = new CopyOnWriteArrayList<>(API.getAllPosts(ClientMain.currentUser));
        }
        Map<String, User> updateUsers = API.getAllUsers(ClientMain.currentUser);
        if(updateUsers!=null){
            users = updateUsers;
        }
        for(User user: users.values()){
            if(API.getAllOfMyPosts(user)!=null){
                user.getUserPosts().clear();
                user.getUserPosts().addAll(API.getAllOfMyPosts(user));
            }
        }
    }

    public static void main(String[] args) {
        InformationTrader.connectToServer();
        launch(args);
    }
}
