package Client;


import common.Command;
import common.Post;
import common.User;

import java.util.HashMap;
import java.util.Map;

public class API {

    public static User SignIn (String username, String password){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.SIGN_IN);
        request.put("username", username);
        request.put("password", password);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (request.get("answer") == null)
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
}
