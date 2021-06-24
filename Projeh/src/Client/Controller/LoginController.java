package Client.Controller;

import Client.API;
import Client.ClientMain;
import Client.PageLoader;
import common.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static Client.ClientMain.currentUser;
import static Client.ClientMain.users;

public class LoginController {

    @FXML
    public TextField usernameBox;
    @FXML
    public PasswordField passwordBox;
    @FXML
    public Button signInBox;
    @FXML
    public Label WrongPasswordErrorLabel;
    @FXML
    public CheckBox showPasswordBox;
    @FXML
    public TextField visiblePassword;


    public void SignIn(ActionEvent actionEvent) throws IOException {
        String username = usernameBox.getText();
        String password;
        if (passwordBox.isVisible())
            password = passwordBox.getText();
        else
            password = visiblePassword.getText();
        User user = API.SignIn(username, password);
        if (user == null){
            WrongPasswordErrorLabel.setVisible(true);
        }
        else {
            WrongPasswordErrorLabel.setVisible(false);
            currentUser = user;
            ClientMain.update();
            API.getAllPosts(currentUser);
            for(User user1 : users.values()){
                API.getMyPosts(user1);
            }
            API.getAllUsers(currentUser);
            PageLoader.load("TimeLine", 340);
        }
    }

    public void showPassword(ActionEvent actionEvent) {
        if (!visiblePassword.isVisible()) {
            visiblePassword.setVisible(true);
            passwordBox.setVisible(false);
            visiblePassword.setText(passwordBox.getText());
        }
        else {
            visiblePassword.setVisible(false);
            passwordBox.setVisible(true);
            passwordBox.setText(visiblePassword.getText());
        }
    }

    public void Exit(ActionEvent actionEvent) {
        System.exit(0);

    }

    public void SignUp(ActionEvent actionEvent) throws IOException {
        PageLoader.load("SignUp");
    }

    public void ForgetPassword(ActionEvent actionEvent) {
    }
}
