package Client.Controller;

import Client.PageLoader;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MyProfilePageController {
    public void Logout(ActionEvent actionEvent) throws IOException {
        PageLoader.load("LoginPage");
    }

    public void EditProfile(ActionEvent actionEvent) {
    }

    public void Back(ActionEvent actionEvent) throws IOException {
        PageLoader.load("TimeLine");
    }
}
