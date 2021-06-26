package Server;

import common.Command;
import common.Comment;
import common.Post;
import common.User;
import javafx.geometry.Pos;

import java.util.*;
import java.util.stream.Collectors;

import static Server.Server.allPosts;
import static Server.Server.allUsers;

public class API {

    public static synchronized Map<String, Object> SignIn (Map<String, Object> request){
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

    public static synchronized Map<String, Object> SignUp (Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User newUser = (User) request.get("user");
        Server.allUsers.put(newUser.getUsername(), newUser);
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.SIGN_UP);
        answer.put("answer", true);
        return answer;
    }

    public static synchronized Map<String, Object> isExistingUsername(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        String username = (String) request.get("username");
        User user = Server.allUsers.get(username);
        boolean exist = user != null;
        answer.put("answer", exist);
        answer.put("command", Command.UNIQUE_USER);
        return answer;
    }

    public static synchronized Map<String, Object> Logout (Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        answer.put("command", Command.LOG_OUT);
        answer.put("answer", true);
        return answer;
    }

    public static synchronized Map<String, Object> Posting (Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        Post newPost = (Post) request.get("post");
        allPosts.add(newPost);
        Server.allUsers.get(newPost.getUser().getUsername()).getUserPosts().add(newPost);
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.POSTING);
        answer.put("answer", true);
        return answer;
    }

    public static synchronized Map<String,Object> getPosts(Map<String,Object> request){
        Map<String,Object> answer = new HashMap<>();
        answer.put("command", Command.GET_POSTS);
        List<Post> sent = new ArrayList<>(allPosts);
        answer.put("posts", sent);
        User user= (User) request.get("user");
        return answer;
    }

    public static synchronized Map<String,Object> getMyPosts(Map<String,Object> request){
        User user = (User) request.get("user");
        String username = user.getUsername();
        Map<String,Object> answer = new HashMap<>();
        answer.put("command", Command.GET_MY_POSTS);
        List<Post> sent= Server.allUsers.get(username).getUserPosts();
        answer.put("myPosts", sent);
        return answer;
    }

    public static synchronized Map<String,Object> getUsers(Map<String,Object> request){
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

    public static synchronized Map<String, Object> EditProfile(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User user = (User) request.get("user");
        ArrayList<Post> tempPosts = user.getUserPosts();
        Server.allUsers.remove(user.getUsername());
        Server.allUsers.put(user.getUsername(), user);
        for (Post p : tempPosts) {
            p.setUser(user);
        }
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.EDIT_PROFILE);
        answer.put("answer", true);
        return answer;
    }

    public static synchronized Map<String, Object> Like(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User user = (User) request.get("user");
        Post likedPost = (Post) request.get("likedPost");
        int likedNumber = 0;
        for (Post p : allPosts) {
            if (p.equals(likedPost))
                p.getLikedUsersList().add(user.getUsername());
        }
        for (Post p : Server.allUsers.get(likedPost.getUser().getUsername()).getUserPosts()) {
            if (p.equals(likedPost)){
                p.getLikedUsersList().add(user.getUsername());
                likedNumber = p.getLikedUsersList().size();
            }
        }
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.LIKE);
        answer.put("answer", likedNumber);
        return answer;
    }

    public static synchronized Map<String, Object> Unlike(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User user = (User) request.get("user");
        Post unlikedPost = (Post) request.get("unlikedPost");
        int likedNumber = 0;
        for (Post p : allPosts) {
            if (p.equals(unlikedPost))
                p.getLikedUsersList().remove(user.getUsername());
        }
        for (Post p : Server.allUsers.get(unlikedPost.getUser().getUsername()).getUserPosts()) {
            if (p.equals(unlikedPost)){
                p.getLikedUsersList().remove(user.getUsername());
                likedNumber = p.getLikedUsersList().size();
            }
        }
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.UNLIKE);
        answer.put("answer", likedNumber);
        return answer;
    }

    public static synchronized Map<String, Object> getLikedMembers(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        Post post = (Post) request.get("post");
        for(Post p: allPosts){
            if(p.equals(post)){
                post=p;
            }
        }
        List<String> likedUsersList = new ArrayList<>();
        answer.put("command", Command.LIKE_MEMBERS);
        answer.put("answer", likedUsersList);
        return answer;
    }

    public static synchronized Map<String, Object> LikeRepostComment_Numbers(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        Post post = (Post) request.get("LRC");
        String LRC = post.getLikes() + "/" + post.getRepost() + "/" + post.getComment();
        answer.put("command", Command.LIKE_REPOST_COMMENT_NUMBERS);
        answer.put("answer", LRC);
        return answer;
    }

    public static synchronized Map<String, Object> Comment(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User user = (User) request.get("user");
        Post post = (Post) request.get("post");
        Comment comment = (Comment) request.get("comment");
        List<Comment> commentList = new ArrayList<>();
        for (Post p :
                Server.allUsers.get(post.getUser().getUsername()).getUserPosts()) {
            if (post.equals(p)) {
                p.getCommentedUsersList().add(post.getUser().getUsername());
                p.getAllComments().add(comment);
                p.setComment(p.getComment() + 1);
                commentList = p.getAllComments();
            }
        }
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.COMMENT);
        answer.put("answer", commentList);
        return answer;
    }

    public static synchronized Map<String, Object> getComments(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        Post post = (Post) request.get("post");
        for (Post p : allPosts) {
            if (post.equals(p))
                post = p;
        }
        answer.put("command", Command.COMMENT_NUMBERS);
        answer.put("answer", post.getAllComments());
        return answer;
    }

    public static synchronized Map<String, Object> Follow (Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User user1 = (User) request.get("following");
        User user2 = (User) request.get("followed");
        Server.allUsers.get(user1.getUsername()).getFollowing().add(user2);
        Server.allUsers.get(user2.getUsername()).getFollower().add(user1);
        DataBase.getInstance().updateDataBase();
        String following_follower = Server.allUsers.get(user1.getUsername()).getFollowing().size()
                + "/" + Server.allUsers.get(user2.getUsername()).getFollower().size();
        answer.put("command", Command.FOLLOW);
        answer.put("answer", following_follower);
        return answer;
    }

    public static synchronized Map<String, Object> Unfollow(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User user1 = (User) request.get("unfollowing");
        User user2 = (User) request.get("unfollowed");
        Server.allUsers.get(user1.getUsername()).getFollowing().remove(user2);
        Server.allUsers.get(user2.getUsername()).getFollower().remove(user1);
        DataBase.getInstance().updateDataBase();
        String following_follower = Server.allUsers.get(user1.getUsername()).getFollowing().size()
                + "/" + Server.allUsers.get(user2.getUsername()).getFollower().size();
        answer.put("command", Command.UNFOLLOW);
        answer.put("answer", following_follower);
        return answer;
    }

    public static synchronized Map<String, Object> getFollowerMembers(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User user = (User) request.get("user");
        List<User> userList =Server.allUsers.get(user.getUsername()).getFollower();
        List<String> usernameList = new ArrayList<>();
        for (User u : userList) {
            usernameList.add(u.getUsername());
        }
        answer.put("command", Command.GET_FOLLOWER_MEMBERS);
        answer.put("answer", usernameList);
        return answer;
    }

    public static synchronized Map<String, Object> getFollowingsMembers(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User user = (User) request.get("user");
        List<User> userList =Server.allUsers.get(user.getUsername()).getFollowing();
        List<String> usernameList = new ArrayList<>();
        for (User u : userList) {
            usernameList.add(u.getUsername());
        }
        answer.put("command", Command.GET_FOLLOWING_MEMBERS);
        answer.put("answer", usernameList);
        return answer;
    }

    public static synchronized Map<String, Object> getInfo(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User userViewer = (User) request.get("userViewer");
        User profileOwner = (User) request.get("profileOwner");
        String Info = Server.allUsers.get(profileOwner.getUsername()).getFollowing().size() + "/"
                + Server.allUsers.get(profileOwner.getUsername()).getFollower().size() + "/"
                + Server.allUsers.get(profileOwner.getUsername()).getUserPosts().size();
        answer.put("command", Command.GET_INFO);
        answer.put("answer", Info);
        return answer;
    }
    
    public static synchronized Map<String, Object> ForgotPassword(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        String firstname = (String) request.get("firstname");
        String surname = (String) request.get("surname");
        String username = (String) request.get("username");
        String password = "";
        for (User user : allUsers.values()) {
            if (user.getUsername().equals(username) && user.getFirstname().equals(firstname)
                    && user.getSurname().equals(surname)) {
                password = user.getPassword();
            }
        }
        answer.put("command", Command.FORGOT_PASSWORD);
        answer.put("answer", password);
        return answer;
    }

    public static synchronized Map<String, Object> Repost(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        Post post = (Post) request.get("post");
        User user = (User) request.get("user");
        int repostNumber = 0;
        for(Post p: allUsers.get(post.getUser().getUsername()).getUserPosts()){
            if(p.equals(post)){
                p.getRepostedUsersList().add(user.getUsername());
                allUsers.get(user.getUsername()).getUserPosts().add(p);
                repostNumber = p.getRepostedUsersList().size();
            }
        }
    }

    public static synchronized Map<String, Object> getRepostMembers(Map<String, Object> request){

    }
}
