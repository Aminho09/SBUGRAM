package Client.Controller;

import Client.API;
import Client.ClientMain;
import Client.PageLoader;
import com.sun.glass.events.MouseEvent;
import common.Post;
import common.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.*;
import java.nio.file.Paths;
import java.util.Objects;

import static Client.ClientMain.*;

public class PostItemController {
    public AnchorPane root;
    public Circle profileImage;
    public Label username;
    public Label title;
    public Button likedButton;
    public Button unlikedButton;
    public Button rePostBack;
    public Button rePost;
    public Button comment;
    public ImageView reposted;
    Post post;
    private int likedNum = 0 , repostedNum = 0, commentedNum = 0;
    static Post staticPost;
    public Label likedNumbers;
    public Label commentNumbers;
    public Label rePostNumbers;
    public static User postOwner;
    public PostItemController(Post post) throws IOException {
        new PageLoader().load("PostItem", this);
        this.post = post;
        postOwner = post.getUser();
    }

    public AnchorPane init() {
        if (post.getUser().getProfileImage() != null) {
            Image image = new Image(new ByteArrayInputStream(post.getUser().getProfileImage()));
            profileImage.setFill(new ImagePattern(image));
        }
        String [] LRC = API.LikeRepostComment_Numbers(post).split("/");
        likedNumbers.setText(post.getLikedUsersList().size()+"");
//        rePostNumbers.setText();
        commentNumbers.setText(post.getCommentedUsersList().size()+"");
        rePostNumbers.setText(post.getRepostedUsersList().size() + "");
        for (String user : post.getLikedUsersList()) {
            if (user.equals(currentUser.getUsername())){
                likedButton.setVisible(true);
                likedButton.toFront();
                unlikedButton.setVisible(false);
                unlikedButton.toBack();
            }
        }

        for (String user : post.getRepostedUsersList()) {
            if (user.equals(currentUser.getUsername())) {
                rePost.setVisible(true);
                rePost.toFront();
                rePostBack.setVisible(false);
                rePostBack.toBack();
            }
        }
        username.setText(post.getWriter());
        title.setText(post.getTitle());

        return root;
    }

    public void Like(ActionEvent actionEvent){
        likedNum = API.like(currentUser, post);
//        System.out.println(likedNum);
//        System.out.println(post.getLikedUsersList());
        likedButton.setVisible(true);
        likedButton.toFront();
        unlikedButton.setVisible(false);
        unlikedButton.toBack();
        likedNumbers.setText(Integer.toString(likedNum));
    }

    public void Unlike(ActionEvent actionEvent) {
        likedNum = API.Unlike(currentUser, post);
        likedButton.setVisible(false);
        likedButton.toBack();
        unlikedButton.setVisible(true);
        unlikedButton.toFront();
        likedNumbers.setText(Integer.toString(likedNum));
    }

    public void Reposting(ActionEvent actionEvent){
        if (!reposted.isVisible() && !post.getUser().getUsername().equals(currentUser.getUsername())){
            post.getPublisher().add(currentUser);
            rePostNumbers.setText(String.valueOf(API.Repost(currentUser, post)));
            currentUser.getUserPosts().add(post);
            ClientMain.update();
            API.getAllPosts(currentUser);
            for(User u: users.values()){
                API.getMyPosts(u);
            }
            reposted.setVisible(true);
        }
    }

    public void commenting(ActionEvent actionEvent) throws IOException {
        staticPost = post;
        PageLoader.load("CommentPage");
    }

    public void ShowPost(ActionEvent actionEvent) throws IOException {
        staticPost = post;

        PageLoader.load("ShowDetailsPost");
    }

    public void seeProfile(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if (post.getUser().equals(currentUser)){
            PageLoader.load("MyProfilePage");
        }
        else
            PageLoader.load("OtherProfilesPage");
    }
}
