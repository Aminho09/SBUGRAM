package Client.Controller;

import Client.API;
import Client.ClientMain;
import Client.PageLoader;
import common.Post;
import common.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        API.getAllPosts(currentUser);
        API.getTimeline(currentUser);
        for(User u: users.values()){
            API.getAllOfMyPosts(u);
        }
        List<Post> posts=API.getAllPosts(currentUser);
        List<String> followingsMembers = API.getFollowingsMembers(currentUser);
        Set<Post> postSet = new HashSet<>();
        List<Post> postList = new ArrayList<>();
        for(Post p: posts){
            assert followingsMembers != null;
            if(followingsMembers.contains(p.getUser().getUsername())) {
                postSet.add(p);
            }
            if(currentUser.getUsername().equals(p.getUser().getUsername())){
                postSet.add(p);
            }
            List<String> pu = p.getPublisher().stream()
                    .map(a-> a.getUsername())
                    .collect(Collectors.toList());
            for(String s: pu){
                if(followingsMembers.contains(s)){
                    postSet.add(p);
                }
                if(currentUser.getUsername().equals(s)){
                    postSet.add(p);
                }
            }
        }
        postList = postSet.stream().sorted().collect(Collectors.toList());
        listOfPosts.setItems(FXCollections.observableArrayList(postList));
        listOfPosts.setCellFactory(PostList -> new PostItem());
    }

    public void post(ActionEvent actionEvent) throws IOException {
        submittingPost.setWriter(currentUser.getUsername());
        submittingPost.setDescription(description.getText());
        submittingPost.setTitle(title.getText());
        submittingPost.setUser(currentUser);
        submittingPost.getPublisher().add(currentUser);
        allPosts.add(submittingPost);
        currentUser.getUserPosts().add(submittingPost);
        API.Posting(submittingPost);
        ClientMain.update();
        API.getAllPosts(currentUser);
        for(User user: users.values()){
            API.getMyPosts(user);
        }
//        listOfPosts.setItems(FXCollections.observableArrayList(allPosts));
//        listOfPosts.setCellFactory(postList -> new PostItem());
        submittingPost = new Post();
        description.setText("");
        title.setText("");
        initialize();
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
        new PageLoader().load("Timeline");
        initialize();
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
