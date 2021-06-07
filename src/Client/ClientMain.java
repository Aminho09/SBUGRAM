package Client;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClientMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.initStage(primaryStage);
        PageLoader.load("LoginPage");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
