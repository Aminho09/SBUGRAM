package Server;

import common.Command;
import common.Post;
import common.User;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static Server.Server.allPosts;

public class API {

    public static Map<String, Object> SignIn (Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        boolean isNullProfile = (Server.allUsers.get(username) == null);
        Map<String,Object> ans = new HashMap<>();
        ans.put("command", Command.SIGN_IN);
        ans.put("exists",!isNullProfile);
        if(isNullProfile){
            return ans;
        }

        User user = Server.allUsers.get(username);
        if (username.equals(user.getUsername()) && password.equals(user.getPassword()))
            ans.put("answer",user);
        else
            ans.put("answer", null);
        return ans;
    }

    public static Map<String, Object> SignUp (Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User newUser = (User) request.get("user");
        Server.allUsers.put(newUser.getUsername(), newUser);
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.SIGN_UP);
        answer.put("answer", true);
        return answer;
    }

    public static Map<String, Object> isValidUsername (Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        String username = (String) request.get("username");
        User user = Server.allUsers.get(username);
        boolean exist = user != null;
        answer.put("answer", exist);
        answer.put("command", Command.UNIQUE_USER);
        return answer;
    }

    public static Map<String, Object> Logout (Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        answer.put("command", Command.LOG_OUT);
        answer.put("answer", true);
        return answer;
    }

    public static Map<String, Object> Posting (Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        Post newPost = (Post) request.get("post");
        allPosts.add(newPost);
        Server.allUsers.get(newPost.getUser().getUsername()).getUserPosts().add(newPost);
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.POSTING);
        answer.put("answer", true);
        return answer;
    }

    public static Map<String,Object> getPosts(Map<String,Object> request){
        Map<String,Object> answer = new HashMap<>();
        answer.put("command", Command.GET_POSTS);
        List<Post> sent = new ArrayList<>(allPosts);
        answer.put("posts", sent);
        User user= (User) request.get("user");
        return answer;
    }

    public static Map<String,Object> getMyPosts(Map<String,Object> request){
        User user = (User) request.get("user");
        String username = user.getUsername();
        Map<String,Object> answer = new HashMap<>();
        answer.put("command", Command.GET_MY_POSTS);
        List<Post> sent= Server.allUsers.get(username).getUserPosts();
        answer.put("myPosts", sent);
        return answer;
    }

    public static Map<String,Object> getUsers(Map<String,Object> request){
        User user = (User) request.get("user");
        String username = user.getUsername();
        Map<String,Object> answer = new HashMap<>();
        answer.put("command", Command.GET_USERS);
        List<User> temp= Server
                .allUsers
                .values()
                .stream()
                .filter(a -> !a.getUsername().equals(username))
                .collect(Collectors.toList());
        List<String> help= Server
                .allUsers
                .keySet()
                .stream()
                .filter(a -> !a.equals(username))
                .collect(Collectors.toList());
        Map<String, User> sent = new HashMap<>();
        int i=0;
        for(String s: help){
            sent.put(s, temp.get(i));
            i++;
        }
        answer.put("users", sent);
        return answer;
    }

    public static Map<String, Object> EditProfile(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User user = (User) request.get("user");
        Server.allUsers.remove(user.getUsername());
        Server.allUsers.put(user.getUsername(), user);
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.EDIT_PROFILE);
        answer.put("answer", true);
        return answer;
    }
}
