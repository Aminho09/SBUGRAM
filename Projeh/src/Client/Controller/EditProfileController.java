package Client.Controller;

import Client.API;
import Client.ClientMain;
import Client.InformationTrader;
import Client.PageLoader;
import common.Gender;
import common.Post;
import common.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Popup;


import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static Client.ClientMain.allPosts;
import static Client.ClientMain.currentUser;

public class EditProfileController {

    public Circle profilePhoto;
    public TextField firstname;
    public TextField surname;
    public TextField yearBirthDate;
    public TextField monthBirthDate;
    public TextField dayBirthDate;
    public MenuButton gender;
    public Label username;
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
    private boolean selectedGender = false;
    private Gender genders;
    byte[] bytes;
    File selectedFile;

    public void initialize(){
        Image image = new Image(new ByteArrayInputStream(currentUser.getProfileImage()));
        profilePhoto.setFill(new ImagePattern(image));
        firstname.setText(currentUser.getFirstname());
        surname.setText(currentUser.getSurname());
        yearBirthDate.setText(Integer.toString(currentUser.getYearBirth()));
        monthBirthDate.setText(Integer.toString(currentUser.getMonthBirth()));
        dayBirthDate.setText(Integer.toString(currentUser.getMonthBirth()));
        gender.setText(currentUser.getGender().toString());
        genders = currentUser.getGender();
        username.setText(currentUser.getUsername());
        password.setText(currentUser.getPassword());
        confirmPassword.setText(currentUser.getPassword());
        selectedGender = true;
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
                && password.getLength() != 0 && confirmPassword.getLength() != 0
                && yearBirthDate.getLength() != 0 && monthBirthDate.getLength() != 0
                && dayBirthDate.getLength() != 0 && confirmPassword.getText().equals(password.getText())
                && (symbol && letter && number)){
            ArrayList<Post> tempPosts = currentUser.getUserPosts();
            currentUser = new User(firstname.getText(), surname.getText(),
                    Integer.parseInt(yearBirthDate.getText()), Integer.parseInt(monthBirthDate.getText()),
                    Integer.parseInt(dayBirthDate.getText()), genders,
                    username.getText(), password.getText(), bytes, currentUser.getUserPosts());
            for (Post p : tempPosts) {
                p.setUser(currentUser);
            }
            for (Post p : allPosts) {
                if (p.getUser().equals(currentUser)) {
                    p.setUser(currentUser);
                }
            }
            if (selectedFile != null){
                currentUser.setProfilePath(selectedFile.getAbsolutePath());
            }
            API.EditProfile(currentUser);
            ClientMain.users.replace(currentUser.getUsername(), currentUser);
            PageLoader.load("MyProfilePage", 340);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have successfully edited your information!");
            alert.setTitle("SBUGRAM");
            alert.setHeaderText("Edit completed");
            alert.showAndWait();
        }
    }

    public void BackToSignInPage(ActionEvent actionEvent) throws IOException {
        PageLoader.load("MyProfilePage", 340);
    }

    public void setProfilePhoto(ActionEvent actionEvent) throws IOException {
        FileChooser uploadImage = new FileChooser();
        selectedFile = uploadImage.showOpenDialog(new Popup());
        if (selectedFile != null){
            FileInputStream inputStream = new FileInputStream(selectedFile);
            bytes = inputStream.readAllBytes();
            Image image = new Image(new ByteArrayInputStream(bytes));
            profilePhoto.setFill(new ImagePattern(image));
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
