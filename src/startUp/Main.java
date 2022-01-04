package startUp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Here, the first window (Stage) is being linked to it's respective FXML file and being displayed.
        Parent root = FXMLLoader.load(getClass().getResource("startPage.fxml"));
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene (root));
        primaryStage.show();
        //The Icon of the stage is set.
        primaryStage.getIcons().add(new Image("startUp/a5960d004b44a98ddf48bc3e69c9af8e_resize copy.png"));

    }

    public static void main(String[] args) {
        //This begins the program by launching the first window.
        launch(args);
    }

}