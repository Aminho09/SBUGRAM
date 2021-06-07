package Client.Controller;

import common.Post;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class TimeLineController {
    public TextArea description;
    public TextField title;
    public ListView<Post> listOfPosts;
    List<Post> allPosts = new ArrayList<>();
    Post submittingPost = new Post();

    public void post(ActionEvent actionEvent) {
        submittingPost.setWriter("Amin");
        submittingPost.setDescription(description.getText());
        submittingPost.setTitle(title.getText());
        allPosts.add(submittingPost);
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
