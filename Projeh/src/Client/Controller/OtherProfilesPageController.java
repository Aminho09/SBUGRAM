package Client.Controller;

import Client.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class OtherProfilesPageController {
    public Circle profileImage;
    public Label usernameLabel;
    public Label postsCounter;
    public Label followingsCounter;
    public Label followersCounter;
    public Button unfollowingButton;
    public Button followingButton;

    public void initialize() {

    }

    public void Unfollow(ActionEvent actionEvent) {
        unfollowingButton.setVisible(true);
        followingButton.setVisible(false);
    }

    public void Follow(ActionEvent actionEvent) {
        unfollowingButton.setVisible(false);
        followingButton.setVisible(true);
    }

    public void Back(ActionEvent actionEvent) throws IOException {
        PageLoader.load("TimeLine");
    }
}
