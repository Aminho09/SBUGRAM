package Client.Controller;

import Client.PageLoader;
import common.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.*;

import static Client.ClientMain.currentPost;
import static Client.ClientMain.currentUser;

public class ShowDetailsPostController {
    public Circle profileImage;
    public Label usernameLabel;
    public Label titleLabel;
    public Label descriptionLabel;
    private Post post = PostItemController.staticPost;


    @FXML
    public void initialize(){

        Image image = new Image(new ByteArrayInputStream(post.getUser().getProfileImage()));
        profileImage.setFill(new ImagePattern(image));
        usernameLabel.setText(PostItemController.staticPost.getWriter());
       descriptionLabel.setText(PostItemController.staticPost.getDescription());
       titleLabel.setText(PostItemController.staticPost.getTitle());
    }

    public void BackToPostsPage(ActionEvent actionEvent) throws IOException {
        PageLoader.load("TimeLine", 340);
    }
}
