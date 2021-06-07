package Client.Controller;

import Client.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

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
        if (!username.equals("Amin") || !password.equals("0312533039")){
            WrongPasswordErrorLabel.setVisible(true);
        }
        else {
            WrongPasswordErrorLabel.setVisible(false);
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
