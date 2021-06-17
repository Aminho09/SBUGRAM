package Client.Controller;

import Client.PageLoader;
import common.Post;
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
    static Post staticPost;
    public Label likedNumbers;
    public Label commentNumbers;
    public Label rePostNumbers;
    public PostItemController(Post post) throws IOException {
        new PageLoader().load("PostItem", this);
        this.post = post;
    }

    public AnchorPane init() {
        File file;
        FileInputStream inputStream = null;
        byte[] bytes;
        Image image;
        try {
            file = new File("C:\\Users\\lenovo\\Desktop\\Ap projectss\\Projeh\\src\\Client\\Controller" +
                    "\\images\\icons8_user_480px.png");
            inputStream = new FileInputStream(file);
            bytes = inputStream.readAllBytes();
            image = new Image(new ByteArrayInputStream(bytes));
            profileImage.setFill(new ImagePattern(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (post.getWriter().equals("Amin")){
            try {
                file = new File("C:\\Users\\lenovo\\Pictures\\20210402_013657.jpg");
                inputStream = new FileInputStream(file);
                bytes = inputStream.readAllBytes();
                image = new Image(new ByteArrayInputStream(bytes));
                profileImage.setFill(new ImagePattern(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        username.setText(post.getWriter());
        title.setText(post.getTitle());

        return root;
    }

    public void Like(ActionEvent actionEvent){
        likedButton.setVisible(true);
        unlikedButton.setVisible(false);
        post.setLikes(post.getLikes() + 1);
        likedNumbers.setText(Long.toString(post.getLikes()));
    }

    public void Unlike(ActionEvent actionEvent) {
        likedButton.setVisible(false);
        unlikedButton.setVisible(true);
        post.setLikes(post.getLikes() - 1);
        likedNumbers.setText(Long.toString(post.getLikes()));
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
}
