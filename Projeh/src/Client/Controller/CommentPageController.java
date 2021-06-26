package Client.Controller;

import Client.API;
import Client.PageLoader;
import common.Comment;
import common.Post;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Client.ClientMain.allPosts;
import static Client.ClientMain.currentUser;

public class CommentPageController {
    public Label usernameLabel;
    public Label titleLabel;
    public Label descriptionLabel;
    public Circle profileImage;
    public ListView<Comment> commentsList = new ListView<>();
    public List<Comment> allComments = new ArrayList<>();
    public TextField commentText;
    public Comment comment = new Comment();
    Post commentingPost = PostItemController.staticPost;

    public void initialize(){
        allComments = API.getComments(PostItemController.staticPost);
        commentsList.setItems(FXCollections.observableArrayList(allComments));
        commentsList.setCellFactory(comment -> new CommentItem());
        Image image = new Image(new ByteArrayInputStream(commentingPost.getUser().getProfileImage()));
        profileImage.setFill(new ImagePattern(image));
        usernameLabel.setText(commentingPost.getWriter());
        titleLabel.setText(commentingPost.getTitle());
    }

    public void BackToPostsPage(ActionEvent actionEvent) throws IOException {
        PageLoader.load("TimeLine");
    }

    public void commentButton(ActionEvent actionEvent) {
        comment.setText(commentText.getText());
        comment.setWriter(currentUser);
        comment.setCommentedPost(PostItemController.staticPost);
        allComments = API.Comment(currentUser, commentingPost, comment);
        assert allComments != null;
        allComments.add(comment);
        commentsList.setItems(FXCollections.observableArrayList(allComments));
        commentsList.setCellFactory(comment -> new CommentItem());
        comment = new Comment();
        comment.setText("");
        commentText.clear();
    }
}
