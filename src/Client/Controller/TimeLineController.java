package Client.Controller;

import Client.API;
import Client.ClientMain;
import common.Post;
import common.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

import Client.ClientMain.*;

import static Client.ClientMain.*;

public class TimeLineController {
    public TextArea description;
    public TextField title;
    public ListView<Post> listOfPosts;
    Post submittingPost = new Post();

    public void initialize (){
        List<Post> posts = API.getAllPosts(currentUser);
        List<Post> postList = new ArrayList<>();
        for (Post p : posts) {
            if(currentUser.getUsername().equals(p.getUser().getUsername())){
                postList.add(p);
            }
        }
        listOfPosts.setItems(FXCollections.observableArrayList(allPosts));
        listOfPosts.setCellFactory(listOfPosts -> new PostItem());
    }

    public void post(ActionEvent actionEvent) {
        submittingPost.setWriter(currentUser.getUsername());
        submittingPost.setDescription(description.getText());
        submittingPost.setTitle(title.getText());
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
}
