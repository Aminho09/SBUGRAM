package Client.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class EditProfileController {

    public Circle profilePhoto;
    public TextField firstname;
    public TextField surname;
    public TextField yearBirthDate;
    public TextField monthBirthDate;
    public TextField dayBirthDate;
    public MenuButton gender;
    public TextField username;
    public TextField password;
    public TextField confirmPassword;
    public Label firstnameNullError;
    public Label surnameNullError;
    public Label birthDateNullError;
    public Label notSelectedGenderError;
    public Label usernameNullError;
    public Label passwordNullError;
    public Label confirmPasswordNullError;
    public Label notMatchedPasswordError;
    public Label invalidPasswordError;
    public Label invalidDateError;

    public void ConfirmButton(ActionEvent actionEvent) {
    }

    public void BackToSignInPage(ActionEvent actionEvent) {
    }

    public void maleGender(ActionEvent actionEvent) {
    }

    public void femaleGender(ActionEvent actionEvent) {
    }

    public void otherGender(ActionEvent actionEvent) {
    }

    public void setProfilePhoto(ActionEvent actionEvent) {
    }
}
