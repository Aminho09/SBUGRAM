package Client.Controller;

import Client.PageLoader;
import common.Post;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.Paths;

public class PostItemController {
    public AnchorPane root;
    public ImageView profileImage;
    public Label username;
    public Label title;
    public Button likedButton;
    public Button unlikedButton;
    Post post;
    static Post staticPost;
    public Label likedNumbers;
    private int likeCountInt = 0;
    public PostItemController(Post post) throws IOException {
        new PageLoader().load("PostItem", this);
        this.post = post;
    }

    public AnchorPane init() {
        username.setText(post.getWriter());
        title.setText(post.getTitle());

        if (post.getWriter().equals("ali alavi"))
            profileImage.setImage(new Image(Paths.get("images/ali_alavi.jpg").toUri().toString()));
        return root;
    }

    public void Like(ActionEvent actionEvent){
        likedButton.setVisible(true);
        unlikedButton.setVisible(false);
        likedNumbers.setText(Integer.toString(++likeCountInt));
    }

    public void Unlike(ActionEvent actionEvent) {
        likedButton.setVisible(false);
        unlikedButton.setVisible(true);
        likedNumbers.setText(Integer.toString(--likeCountInt));
    }

    public void ShowPost(ActionEvent actionEvent) throws IOException {
        staticPost = post;
        PageLoader.load("ShowDetailsPost");
    }
}
