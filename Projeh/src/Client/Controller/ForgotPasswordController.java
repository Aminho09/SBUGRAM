package Client.Controller;

import Client.PageLoader;
import Client.API;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ForgotPasswordController {
    public Label WrongInfoErrorLabel;
    public TextField firstnameText;
    public TextField surnameText;
    public TextField usernameText;
    public Label passwordGiven;

    public void Verifying(ActionEvent actionEvent) {
        String firstname = firstnameText.getText();
        String surname = surnameText.getText();
        String username = usernameText.getText();
        String password = API.ForgotPassword(firstname, surname, username);
        if (!password.equals("")){
            passwordGiven.setText("Your password is: " + password);
            WrongInfoErrorLabel.setVisible(false);
        }
        else{
            passwordGiven.setText("");
            WrongInfoErrorLabel.setVisible(true);
        }
    }

    public void Exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void SignUp(ActionEvent actionEvent) throws IOException {
        PageLoader.load("SignUp");
    }

    public void Sign_In(ActionEvent actionEvent) throws IOException {
        PageLoader.load("LoginPage");
    }
}
