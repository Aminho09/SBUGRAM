package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ShowDetailsPostController {
    public ImageView profileImage;
    public Label usernameLabel;
    public Label titleLabel;
    public Label descriptionLabel;

    @FXML
    public void initialize(){
       usernameLabel.setText(PostItemController.staticPost.getWriter());
       descriptionLabel.setText(PostItemController.staticPost.getDescription());
       titleLabel.setText(PostItemController.staticPost.getTitle());
    }

    public void BackToPostsPage(ActionEvent actionEvent) throws IOException {
        PageLoader.load("TimeLine", 340);
    }
}
