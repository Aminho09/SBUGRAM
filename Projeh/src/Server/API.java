package Server;

import common.*;
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
        if (user != null){
            System.out.println(username + " login");
            System.out.println("time: " + Time.getTime());
        }
        return ans;
    }

    public static synchronized Map<String, Object> SignUp (Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User newUser = (User) request.get("user");
        Server.allUsers.put(newUser.getUsername(), newUser);
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.SIGN_UP);
        answer.put("answer", true);
        System.out.println(newUser.getUsername() + " register " + newUser.getProfilePath());
        System.out.println("time: " + Time.getTime());
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
        User user = (User) request.get("user");
        answer.put("command", Command.LOG_OUT);
        answer.put("answer", true);
        System.out.println(user.getUsername() + " logout");
        System.out.println("time: " + Time.getTime());
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
        System.out.println("Message: " + newPost.getTitle() + ", " + newPost.getUser().getUsername());
        System.out.println("time: " + Time.getTime());
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
        System.out.println(user.getUsername() + "update info");
        System.out.println("Message: " + user.getProfilePath());
        System.out.println("time: " + Time.getTime());
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
        System.out.println(user.getUsername() + ", like");
        System.out.println(likedPost.getUser().getUsername() + ", " + likedPost.getTitle());
        System.out.println("time: " + Time.getTime());
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
        System.out.println(user.getUsername() + ", Unlike");
        System.out.println(unlikedPost.getUser().getUsername() + ", " + unlikedPost.getTitle());
        System.out.println("time: " + Time.getTime());
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
        System.out.println(user.getUsername() + ", Comment");
        System.out.println("Message: " + post.getTitle());
        System.out.println("time: " + Time.getTime());
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
        System.out.println(user1.getUsername() + " follow");
        System.out.println("Message: " + user2.getUsername());
        System.out.println("time: " + Time.getTime());
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
        System.out.println(user1.getUsername() + " unfollow");
        System.out.println("Message: " + user2.getUsername());
        System.out.println("time: " + Time.getTime());
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
        System.out.println(userViewer.getUsername() + " get info " + profileOwner.getUsername());
        System.out.println("Message: " + profileOwner.getUsername() + " " + profileOwner.getProfilePath());
        System.out.println("time: " + Time.getTime());
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
        Post post = (Post) request.get("repost");
        User user = (User) request.get("user");
        int repostNumber = 0;
        for(Post p: allUsers.get(post.getUser().getUsername()).getUserPosts()){
            if(p.equals(post)){
                p.getRepostedUsersList().add(user.getUsername());
                allUsers.get(user.getUsername()).getUserPosts().add(p);
                repostNumber = p.getRepostedUsersList().size();
            }
        }
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.REPOST);
        answer.put("answer", repostNumber);
        System.out.println(user.getUsername() + " repost");
        System.out.println("Message: " + post.getUser().getUsername() + ", " + post.getTitle());
        System.out.println("time: " + Time.getTime());
        return answer;
    }

    public static synchronized Map<String, Object> getRepostMembers(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        Post post = (Post) request.get("post");
        for(Post p: allPosts){
            if(p.equals(post)){
                post=p;
            }
        }
        List<String> list = post.getRepostedUsersList();
        DataBase.getInstance().updateDataBase();
        answer.put("command", Command.GET_REPOST_MEMBERS);
        answer.put("answer", list);
        return answer;
    }

    public static synchronized Map<String, Object> getTimeline(Map<String, Object> request){
        Map<String, Object> answer = new HashMap<>();
        User user = (User) request.get("user");
        List<Post> postList = new ArrayList<>(allUsers.get(user.getUsername()).getUserPosts());
        for(User u: allUsers.get(user.getUsername()).getFollowing()){
            postList.addAll(allUsers.get(u.getUsername()).getUserPosts());
        }
        answer.put("command", Command.GET_TIMELINE);
        answer.put("answer", postList);
        System.out.println(user.getUsername() + " get posts list");
        System.out.println("time: " + Time.getTime());
        return answer;
    }
}
