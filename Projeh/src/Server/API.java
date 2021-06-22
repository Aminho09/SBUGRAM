package Server;

import common.Command;
import common.Post;
import common.User;
import javafx.geometry.Pos;

import java.util.HashMap;
import java.util.Map;

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
        Server.allPosts.add(newPost);
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.POSTING);
        answer.put("answer", true);
        return answer;
    }


}
