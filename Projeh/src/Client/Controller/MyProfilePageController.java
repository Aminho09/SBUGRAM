package Client.Controller;

import Client.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class MyProfilePageController {
    public Circle profileImage;
    public Label usernameLabel;
    public Label followersCounter;
    public Label followingsCounter;
    public Label postsCounter;

    public void Logout(ActionEvent actionEvent) throws IOException {
        PageLoader.load("LoginPage");
    }

    public void EditProfile(ActionEvent actionEvent) {
    }

    public void Back(ActionEvent actionEvent) throws IOException {
        PageLoader.load("TimeLine");
    }
}
