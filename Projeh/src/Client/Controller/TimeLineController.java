package Client.Controller;

import Client.API;
import Client.ClientMain;
import Client.PageLoader;
import common.Post;
import common.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Client.ClientMain.*;

import static Client.ClientMain.*;
import static Client.Controller.PostItemController.postOwner;

public class TimeLineController {
    public TextArea description;
    public TextField title;
    public ListView<Post> listOfPosts;
    public TextField searchMembers;
    public Label notExistedMember;
    Post submittingPost = new Post();

    public void initialize (){
        ClientMain.update();
        API.getAllUsers(currentUser);
        for(User user: users.values()){
            API.getAllOfMyPosts(user);
        }
        List<String> followerMembers = API.getFollowerMembers(currentUser);
        List<Post> posts = API.getAllPosts(currentUser);
        Set<Post> postSet = new HashSet<>();
        for (Post p : posts) {
            assert followerMembers != null;
            if (followerMembers.contains(p.getUser().getUsername())){
                postSet.add(p);
            }
            if(currentUser.getUsername().equals(p.getUser().getUsername())){
                postSet.add(p);
            }
        }
        listOfPosts.setItems(FXCollections.observableArrayList(allPosts));
        listOfPosts.setCellFactory(listOfPosts -> new PostItem());
        ClientMain.update();

    }

    public void post(ActionEvent actionEvent) {
        submittingPost.setWriter(currentUser.getUsername());
        submittingPost.setDescription(description.getText());
        submittingPost.setTitle(title.getText());
        submittingPost.setUser(currentUser);
        allPosts.add(submittingPost);
        currentUser.getUserPosts().add(submittingPost);
        API.Posting(submittingPost);
        ClientMain.update();
        API.getAllPosts(currentUser);
        for(User user: users.values()){
            API.getMyPosts(user);
        }
        listOfPosts.setItems(FXCollections.observableArrayList(allPosts));
        listOfPosts.setCellFactory(postList -> new PostItem());
        submittingPost = new Post();
        description.setText("");
        title.setText("");
    }

    public void Exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void Logout(ActionEvent actionEvent) throws IOException {
        API.Logout(currentUser);
        currentUser = new User();
        PageLoader.load("LoginPage", 600);
    }

    public void refresh (ActionEvent actionEvent) throws IOException {
        ClientMain.update();
        API.getAllPosts(currentUser);
        for(User u: users.values()){
            API.getMyPosts(u);
        }
        API.getAllUsers(currentUser);
        PageLoader.load("TimeLine");
        List<User> acc=new ArrayList<>();

        List<Post> posts = API.getAllPosts(currentUser);
        Set<Post> t=new HashSet<>();
        for(Post p: posts){

            if(currentUser.getUsername().equals(p.getUser().getUsername())){
                t.add(p);
            }
//            List<String> pu=p.getPublisher().stream()
//                    .map(a-> a.getUsername())
//                    .collect(Collectors.toList());
//            for(String s: pu){
//                if(f.contains(s)){
//                    assert m != null;
//                    if (!(m.contains(p.getUser().getUsername()))) {
//                        t.add(p);
//                    }
//                }
//                if(currentUser.getUsername().equals(s)){
//                    t.add(p);
//                }
//            }
        }
//        API.getTimeline(currentUser);
        listOfPosts.setItems(FXCollections.observableArrayList(t));
        listOfPosts.setCellFactory(PostList -> new PostItem());
//        myPosts.setItems(FXCollections.observableArrayList(currentUser.getPosts()));
//        myPosts.setCellFactory(myPosts -> new PostItem());
//        String temp=API.getNumbers(currentUser, currentUser);
//        following.setText(String.valueOf(Integer.parseInt(temp.substring(0, temp.indexOf("|")))));
//        follower.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")))));
//        post.setText(String.valueOf(Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1))));
//        List<User> shown= users.values().stream()
//                .filter(a-> ((API.getMassaged(currentUser).contains(a.getUsername()))&&(!(blockers.contains(a.getUsername())))))
//                .collect(Collectors.toList());
//        Massages.setItems(FXCollections.observableArrayList(shown));
//        Massages.setCellFactory(Massages -> new DirectUserItem());
    }

    public void SearchButton(ActionEvent actionEvent) throws IOException {
        String username = searchMembers.getText();
        User relatedUser = users.get(username);
        if (relatedUser == null) {
            notExistedMember.setVisible(true);
        } else {
            postOwner = relatedUser;
            notExistedMember.setVisible(false);
            PageLoader.load("OtherProfilesPage");
        }
        searchMembers.clear();
    }
}
