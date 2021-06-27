package Client;


import common.Command;
import common.Comment;
import common.Post;
import common.User;
import javafx.geometry.Pos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static Boolean isExistingUsername(String username){
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
        Map<String,Object> answer = InformationTrader.serve(request);
        return answer;
    }

    public static List<Post> getAllPosts(User user){
        Map<String,Object> all=getPosts(user);
        return (List<Post>) all.get("posts");
    }

    public static Map<String,Object> getMyPosts(User user){
        Map<String,Object> request=new HashMap<>();
        request.put("command", Command.GET_MY_POSTS);
        request.put("user", user);
        Map<String,Object> answer = InformationTrader.serve(request);
        return answer;
    }

    public static List<Post> getAllOfMyPosts(User user){
        Map<String,Object> all=getMyPosts(user);
        return (List<Post>) all.get("myPosts");
    }

    public static Map<String, Object> getUsers(User user){
        Map<String,Object> request = new HashMap<>();
        request.put("command", Command.GET_USERS);
        request.put("user", user);
        request.put("users", ClientMain.users);
        Map<String,Object> answer = InformationTrader.serve(request);
        return answer;
    }

    public static Map<String, User> getAllUsers(User user){
        Map<String,Object> all=getUsers(user);
        return (Map<String, User>) all.get("users");
    }

    public static Boolean EditProfile (User user){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.EDIT_PROFILE);
        request.put("user", user);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (Boolean) answer.get("answer");
    }

    public static Boolean Logout(User user){
        Map<String,Object> request = new HashMap<>();
        request.put("command", Command.LOG_OUT);
        request.put("user", user);
        Map<String,Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (Boolean) answer.get("answer");
    }

    public static Integer like(User user, Post likedPost){
        Map<String,Object> request = new HashMap<>();
        request.put("command", Command.LIKE);
        request.put("user", user);
        request.put("likedPost", likedPost);
        Map<String,Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null){
            return null;
        }
        int x = (int) answer.get("answer");
        return x;
    }

    public static Integer Unlike(User user, Post unlikedPost){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.UNLIKE);
        request.put("user", user);
        request.put("unlikedPost", unlikedPost);
        Map<String, Object> answer = InformationTrader.serve(request);
        int x = (int) answer.get("answer");
        return x;
    }

    public static List<User> getLikedMembers(Post post){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.LIKE_MEMBERS);
        request.put("post", post);
        Map<String, Object> answer = InformationTrader.serve(request);
        return (List<User>) answer.get("answer");
    }

    public static String LikeRepostComment_Numbers(Post post){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.LIKE_REPOST_COMMENT_NUMBERS);
        request.put("LRC", post);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (String) answer.get("answer");
    }

    public static List<Comment> Comment (User user, Post post, Comment comment){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.COMMENT);
        request.put("user", user);
        request.put("post", post);
        request.put("comment", comment);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (List<Comment>) answer.get("answer");
    }

    public static List<Comment> getComments (Post post){
        Map<String, Object> request = new HashMap<>();
        request.put("command",Command.COMMENT_NUMBERS);
        request.put("post", post);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (List<Comment>) answer.get("answer");
    }

    public static String Follow (User user1, User user2){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.FOLLOW);
        request.put("following", user1);
        request.put("followed", user2);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (String) answer.get("answer");
    }

    public static String Unfollow (User user1, User user2){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.UNFOLLOW);
        request.put("unfollowing", user1);
        request.put("unfollowed", user2);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (String) answer.get("answer");
    }

    public static List<String> getFollowerMembers (User user){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.GET_FOLLOWER_MEMBERS);
        request.put("user", user);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (List<String>) answer.get("answer");
    }

    public static List<String> getFollowingsMembers (User user){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.GET_FOLLOWING_MEMBERS);
        request.put("user", user);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (List<String>) answer.get("answer");
    }

    public static String getInfo(User userViewer, User profileOwner){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.GET_INFO);
        request.put("userViewer", userViewer);
        request.put("profileOwner", profileOwner);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return null;
        return (String) answer.get("answer");
    }

    public static String ForgotPassword(String firstname, String surname, String username){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.FORGOT_PASSWORD);
        request.put("firstname", firstname);
        request.put("surname", surname);
        request.put("username", username);
        Map<String, Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null)
            return "";
        return (String) answer.get("answer");
    }

    public static Integer Repost(User user, Post post){
        Map<String,Object> request=new HashMap<>();
        request.put("command", Command.REPOST);
        request.put("user", user);
        request.put("repost", post);
        Map<String,Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null){
            return null;
        }
        return (Integer) answer.get("answer");
    }

    public static List<String> getRepostMembers(Post post){
        Map<String,Object> request = new HashMap<>();
        request.put("command", Command.GET_REPOST_MEMBERS);
        request.put("post", post);
        Map<String,Object> answer = InformationTrader.serve(request);
        if (answer.get("answer") == null){
            return null;
        }
        return (List<String>) answer.get("answer");
    }

    public static List<Post> getTimeline(User user){
        Map<String, Object> request = new HashMap<>();
        request.put("command", Command.GET_TIMELINE);
        request.put("user", user);
        request.put("posts", ClientMain.allPosts);
        Map<String, Object> answer = InformationTrader.serve(request);
        return (List<Post>) answer.get("answer");
    }
}
