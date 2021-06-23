package Client;


import common.Command;
import common.Post;
import common.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API {

    public static User SignIn (String username, String password){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.SIGN_IN);
        request.put("username", username);
        request.put("password", password);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (User) answer.get("answer");
    }

    public static Boolean SignUp (User newUser){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.SIGN_UP);
        request.put("user", newUser);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (Boolean) answer.get("answer");
    }

    public static Boolean isValidName (String username){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.UNIQUE_USER);
        request.put("username", username);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (Boolean) answer.get("answer");
    }

    public static Boolean Posting (Post newPost){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.POSTING);
        request.put("post", newPost);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (Boolean) answer.get("answer");
    }

    public static Map<String, Object> getPosts(User user){
        Map<String,Object> request =new HashMap<>();
        request .put("command", Command.GET_POSTS);
        request .put("posts", ClientMain.allPosts);
        request .put("user", user);
        Map<String,Object> received = InformationTrader.serve(request);
        return received;
    }

    public static List<Post> getAllPosts(User user){
        Map<String,Object> all=getPosts(user);
        return (List<Post>) all.get("posts");
    }

    public static Map<String,Object> getMyPosts(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("command", Command.GET_MY_POSTS);
        toSend.put("user", user);
        Map<String,Object> received = InformationTrader.serve(toSend);
        return received;
    }

    public static List<Post> getAllOfMyPosts(User user){
        Map<String,Object> all=getMyPosts(user);
        return (List<Post>) all.get("myPosts");
    }

    public static Map<String, Object> getUsers(User user){
        Map<String,Object> toSend=new HashMap<>();
        toSend.put("command", Command.GET_USERS);
        toSend.put("user", user);
        toSend.put("users", ClientMain.users);
        Map<String,Object> received = InformationTrader.serve(toSend);
        return received;
    }

    public static Map<String, User> getAllUsers(User user){
        Map<String,Object> all=getUsers(user);
        return (Map<String, User>) all.get("users");
    }
}
