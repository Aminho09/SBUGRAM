package Client.Controller;

import Client.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class CommentPageController {
    public Label usernameLabel;
    public Label titleLabel;
    public Label descriptionLabel;
    public Circle profileImage;
    public ListView<String> commentsList;
    public TextField commentText;

    public void BackToPostsPage(ActionEvent actionEvent) throws IOException {
        PageLoader.load("TimeLine");
    }

    public void commentButton(ActionEvent actionEvent) {
    }
}
