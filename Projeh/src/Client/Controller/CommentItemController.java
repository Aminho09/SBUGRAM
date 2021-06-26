package Client.Controller;

import Client.PageLoader;
import common.Comment;
import common.Post;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

public class CommentItemController {
    private Comment comment;
    public AnchorPane root;
    @FXML
    Circle profileImage;
    public Text commentText;



    public CommentItemController(Comment comment) throws IOException {
        new PageLoader().load("CommentItem", this);
        this.comment = comment;
    }

    public AnchorPane init(){
        if (comment.getWriter().getProfileImage() != null) {
            javafx.scene.image.Image image = new Image(new ByteArrayInputStream(comment.getWriter().getProfileImage()));
            profileImage.setFill(new ImagePattern(image));
        }
        commentText.setText(comment.getWriter().getUsername() + ": " + comment.getText());
        return root;
    }
}
