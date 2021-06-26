package Client.Controller;

import Client.API;
import Client.PageLoader;
import common.Post;
import common.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static Client.ClientMain.currentUser;

public class MyProfilePageController {
    public Circle profileImage;
    public Label usernameLabel;
    public Label followersCounter;
    public Label followingsCounter;
    public Label postsCounter;
    public ListView<Post> myListPosts;

    public void initialize() {
        Image image = new Image(new ByteArrayInputStream(currentUser.getProfileImage()));
        profileImage.setFill(new ImagePattern(image));
        usernameLabel.setText(currentUser.getUsername());
        postsCounter.setText(Integer.toString(currentUser.getUserPosts().size()));
        followersCounter.setText(Integer.toString(currentUser.getFollower().size()));
        followingsCounter.setText(Integer.toString(currentUser.getFollowing().size()));
        System.out.println(currentUser.getUserPosts());
        myListPosts.setItems(FXCollections.observableArrayList(currentUser.getUserPosts()));
        myListPosts.setCellFactory(posts -> new PostItem());
    }

    public void Logout(ActionEvent actionEvent) throws IOException {
        API.Logout(currentUser);
        currentUser = new User();
        PageLoader.load("LoginPage", 600);
    }

    public void EditProfile(ActionEvent actionEvent) throws IOException {
        PageLoader.load("EditProfile", 600);
    }

    public void Back(ActionEvent actionEvent) throws IOException {
        PageLoader.load("TimeLine");
    }

    public void Refresh(ActionEvent actionEvent) throws IOException {
        PageLoader.load("MyProfilePage");
    }
}
