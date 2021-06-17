package Client.Controller;

import Client.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.*;

public class ShowDetailsPostController {
    public Circle profileImage;
    public Label usernameLabel;
    public Label titleLabel;
    public Label descriptionLabel;

    @FXML
    public void initialize(){
        File file = new File("C:\\Users\\lenovo\\Desktop\\Ap projectss\\Projeh\\src\\Client\\Controller" +
                "\\images\\icons8_user_480px.png");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] bytes = inputStream.readAllBytes();
            Image image = new Image(new ByteArrayInputStream(bytes));
            profileImage.setFill(new ImagePattern(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
       usernameLabel.setText(PostItemController.staticPost.getWriter());
       descriptionLabel.setText(PostItemController.staticPost.getDescription());
       titleLabel.setText(PostItemController.staticPost.getTitle());
    }

    public void BackToPostsPage(ActionEvent actionEvent) throws IOException {
        PageLoader.load("TimeLine", 340);
    }
}
