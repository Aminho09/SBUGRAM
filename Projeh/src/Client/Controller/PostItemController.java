package Client.Controller;

import Client.API;
import Client.PageLoader;
import com.sun.glass.events.MouseEvent;
import common.Post;
import common.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.*;
import java.nio.file.Paths;
import java.util.Objects;

import static Client.ClientMain.currentUser;
import static Client.ClientMain.currentPost;

public class PostItemController {
    public AnchorPane root;
    public Circle profileImage;
    public Label username;
    public Label title;
    public Button likedButton;
    public Button unlikedButton;
    public Button rePostBack;
    public Button rePost;
    public Button commentBack;
    public Button comment;
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
        likedNumbers.setText(LRC[0]);
        rePostNumbers.setText(LRC[1]);
        commentNumbers.setText(LRC[2]);
        for (User user : post.getLikedUsersList()) {
            if (user.equals(currentUser)){
                likedButton.setVisible(true);
                unlikedButton.setVisible(false);
            }

        }
        username.setText(post.getWriter());
        title.setText(post.getTitle());

        return root;
    }

    public void Like(ActionEvent actionEvent){
        likedNum = API.like(currentUser, post);
        System.out.println(post.getLikedUsersList());
        likedButton.setVisible(true);
        unlikedButton.setVisible(false);
        likedNumbers.setText(Integer.toString(likedNum));
    }

    public void Unlike(ActionEvent actionEvent) {
        likedNum = API.Unlike(currentUser, post);
        likedButton.setVisible(false);
        unlikedButton.setVisible(true);
        likedNumbers.setText(Integer.toString(likedNum));
    }

    public void Reposting(ActionEvent actionEvent){
        rePost.setVisible(true);
        rePostBack.setVisible(false);
        post.setRepost(post.getRepost() + 1);
        rePostNumbers.setText(Long.toString(post.getRepost()));
    }

    public void rePostingBack(ActionEvent actionEvent){
        rePost.setVisible(false);
        rePostBack.setVisible(true);
        post.setRepost(post.getRepost() - 1);
        rePostNumbers.setText(Long.toString(post.getRepost()));
    }

    public void commenting(ActionEvent actionEvent){
        comment.setVisible(true);
        commentBack.setVisible(false);
        post.setComment(post.getComment() + 1);
        commentNumbers.setText(Long.toString(post.getComment()));
    }

    public void commentingBack(ActionEvent actionEvent){
        comment.setVisible(false);
        commentBack.setVisible(true);
        post.setComment(post.getComment() - 1);
        commentNumbers.setText(Long.toString(post.getComment()));
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
