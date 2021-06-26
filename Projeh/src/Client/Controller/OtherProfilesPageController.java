package Client.Controller;

import Client.API;
import Client.ClientMain;
import Client.PageLoader;
import common.Post;
import common.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static Client.ClientMain.allPosts;
import static Client.ClientMain.currentUser;
import static Client.Controller.PostItemController.postOwner;

public class OtherProfilesPageController {
    public Circle profileImage;
    public Label usernameLabel;
    public Label postsCounter;
    public Label followingsCounter;
    public Label followersCounter;
    private int followerNumber = 0, followingNumber = 0;
    public Button unfollowingButton;
    public Button followingButton;
    public ListView<Post> userListPost;
    private User userProfile = postOwner;

    public void initialize() {
        Image image = new Image(new ByteArrayInputStream(userProfile.getProfileImage()));
        profileImage.setFill(new ImagePattern(image));
        usernameLabel.setText(userProfile.getUsername());
        postsCounter.setText(Integer.toString(userProfile.getUserPosts().size()));
        followersCounter.setText(Integer.toString(userProfile.getFollower().size()));
        followingsCounter.setText(Integer.toString(userProfile.getFollowing().size()));
        String info =API.getInfo(currentUser, postOwner);
        followingsCounter.setText(String.valueOf(Integer.parseInt(info.substring(0, info.indexOf("/")))));
        followersCounter.setText(String.valueOf(Integer.parseInt(info.substring(info.indexOf("/") + 1, info.lastIndexOf("/")))));
        postsCounter.setText(String.valueOf(Integer.parseInt(info.substring(info.lastIndexOf("/") + 1))));
        List<String> followers = API.getFollowerMembers(postOwner);
        assert followers != null;
        if (followers.contains(currentUser.getUsername())){
            unfollowingButton.setVisible(false);
            followingButton.setVisible(true);
        }
        userListPost.setItems(FXCollections.observableArrayList(userProfile.getUserPosts()));
        userListPost.setCellFactory(posts -> new PostItem());
    }

    public void Unfollow(ActionEvent actionEvent) {
        String temp= API.Unfollow(currentUser, userProfile);
        followersCounter.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("/") + 1))));
        unfollowingButton.setVisible(true);
        followingButton.setVisible(false);
    }

    public void Follow(ActionEvent actionEvent) {
        String temp= API.Follow(ClientMain.currentUser, userProfile);
        followersCounter.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("/") + 1))));
        unfollowingButton.setVisible(false);
        followingButton.setVisible(true);
    }

    public void Back(ActionEvent actionEvent) throws IOException {
        PageLoader.load("TimeLine");
    }

    public void Refresh(ActionEvent actionEvent) throws IOException {
        PageLoader.load("OtherProfilesPage");
    }
}
