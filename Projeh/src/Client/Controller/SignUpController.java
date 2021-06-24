package Client.Controller;

import Client.API;
import Client.ClientMain;
import common.Gender;
import Client.PageLoader;
import common.User;
import javafx.event.ActionEvent;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.*;

import static Client.ClientMain.*;

public class SignUpController {


    public Circle profilePhoto;
    public TextField firstname;
    public TextField surname;
    public TextField username;
    public TextField password;
    public TextField confirmPassword;
    public MenuButton gender;
    public TextField yearBirthDate;
    public TextField monthBirthDate;
    public TextField dayBirthDate;
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
    private boolean selectedGender;
    private Gender genders;
    private byte[] bytes;

    public void initialize() throws IOException {
        File file = new File("./src/Client/Controller/images/icons8_user_480px.png");
        FileInputStream inputStream = new FileInputStream(file);
        bytes = inputStream.readAllBytes();
        Image image = new Image(new ByteArrayInputStream(bytes));
        profilePhoto.setFill(new ImagePattern(image));
    }

    public void setProfilePhoto(ActionEvent actionEvent) throws IOException {
        FileChooser uploadImage = new FileChooser();
        File selectedFile = uploadImage.showOpenDialog(new Popup());
        if (selectedFile != null){
            FileInputStream inputStream = new FileInputStream(selectedFile);
            bytes = inputStream.readAllBytes();
            Image image = new Image(new ByteArrayInputStream(bytes));
            profilePhoto.setFill(new ImagePattern(image));
        }
    }

    public void BackToSignInPage(ActionEvent actionEvent) throws IOException {
        PageLoader.load("LoginPage");
    }

    public void ConfirmButton(ActionEvent actionEvent) throws IOException {
        firstnameNullError.setVisible(false);
        surnameNullError.setVisible(false);
        birthDateNullError.setVisible(false);
        notSelectedGenderError.setVisible(false);
        usernameNullError.setVisible(false);
        passwordNullError.setVisible(false);
        confirmPasswordNullError.setVisible(false);
        invalidPasswordError.setVisible(false);
        notMatchedPasswordError.setVisible(false);
        invalidDateError.setVisible(false);
        if (!(confirmPassword.getText().equals(password.getText()))
                && password.getLength() >= 8 && confirmPassword.getLength() >= 8){
            notMatchedPasswordError.setVisible(true);
        }
        if (confirmPassword.getLength() == 0) {
            confirmPasswordNullError.setVisible(true);
        }
        if (password.getLength() == 0) {
            passwordNullError.setVisible(true);
        }
        if (firstname.getLength() == 0) {
            firstnameNullError.setVisible(true);
        }
        if (surname.getLength() == 0) {
            surnameNullError.setVisible(true);
        }
        if (yearBirthDate.getLength() == 0 || dayBirthDate.getLength() == 0 || monthBirthDate.getLength() == 0) {
            birthDateNullError.setVisible(true);
        }
        if (!selectedGender){
            notSelectedGenderError.setVisible(true);
        }
        if (username.getLength() == 0) {
            usernameNullError.setVisible(true);
        }
        if (password.getLength() < 8 && password.getLength() != 0) {
            invalidPasswordError.setVisible(true);
        }
            String stringPassword = password.getText();
            boolean number = false;
            boolean letter = false;
            boolean symbol = false;
            for (int i = 0; i < stringPassword.length(); i++){
                if (stringPassword.charAt(i) >= 48 && stringPassword.charAt(i) <= 57)
                    number = true;
                else if (stringPassword.charAt(i) >= 65 && stringPassword.charAt(i) <= 90)
                    letter = true;
                else if (stringPassword.charAt(i) >= 97 && stringPassword.charAt(i) <= 122)
                    letter = true;
                else if (stringPassword.charAt(i) <= 32){
                    invalidPasswordError.setVisible(true);
                    break;
                } else
                    symbol = true;
            }
        if (password.getLength() >= 8 && !(symbol && letter && number)){
            invalidPasswordError.setVisible(true);
        }
        if (validBirthDate() == -1){
            invalidDateError.setVisible(true);
        }
        else if (selectedGender && firstname.getLength() != 0 && surname.getLength() != 0
                && username.getLength() != 0 && password.getLength() != 0
                && confirmPassword.getLength() != 0 && yearBirthDate.getLength() != 0
                && monthBirthDate.getLength() != 0 && dayBirthDate.getLength() != 0
                && confirmPassword.getText().equals(password.getText()) && (symbol && letter && number)){
            currentUser = new User(firstname.getText(), surname.getText(),
                    Integer.parseInt(yearBirthDate.getText()), Integer.parseInt(monthBirthDate.getText()),
                    Integer.parseInt(dayBirthDate.getText()), genders,
                    username.getText(), password.getText(), bytes);
            API.SignUp(currentUser);
            ClientMain.users.put(currentUser.getUsername(), currentUser);
            PageLoader.load("LoginPage");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have successfully signed up!");
            alert.setTitle("SBUGRAM");
            alert.setHeaderText("Signed-up");
            alert.showAndWait();
        }
    }

    private int validBirthDate(){
        int yearBirth, monthBirth, dayBirth;
        if (yearBirthDate.getLength() == 0 || dayBirthDate.getLength() == 0 || monthBirthDate.getLength() == 0)
            return 1;
        String fullBirthDate = yearBirthDate.getText() + monthBirthDate.getText() + dayBirthDate.getText();
        for (int i = 0; i < fullBirthDate.length(); i++){
            if (fullBirthDate.charAt(i) < '0' || fullBirthDate.charAt(i) > '9'){
                return -1;
            }
        }
        yearBirth = Integer.parseInt(yearBirthDate.getText());
        monthBirth = Integer.parseInt(monthBirthDate.getText());
        dayBirth = Integer.parseInt(dayBirthDate.getText());
        if (yearBirth > 2022 || yearBirth < 1900)
            return -1;
        if (monthBirth < 1 || monthBirth > 12)
            return -1;
        if (dayBirth < 1 || dayBirth > 31){
            return -1;
        }
        if (monthBirth == 2){
            if (dayBirth > 28)
                return -1;
        }
        if (monthBirth == 4)
            if (dayBirth > 30){
                return -1;
            }
        if (monthBirth == 6)
            if (dayBirth > 30){
                return -1;
            }
        if (monthBirth == 9)
            if (dayBirth > 30){
                return -1;
            }
        if (monthBirth == 11)
            if (dayBirth > 30){
                return -1;
            }
        return 0;
    }

    public void maleGender(ActionEvent actionEvent) {
        selectedGender = true;
        gender.setText("Male");
        genders = Gender.Male;
    }

    public void femaleGender(ActionEvent actionEvent) {
        selectedGender = true;
        gender.setText("Female");
        genders = Gender.Female;
    }

    public void otherGender(ActionEvent actionEvent) {
        selectedGender = true;
        gender.setText("Other");
        genders = Gender.Other;
    }
}