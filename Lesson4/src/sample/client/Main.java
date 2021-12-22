package sample.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 450, 700);
        String stylesheet = getClass().getResource("/CSS/style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        primaryStage.setScene(scene);
        Controller controller = loader.getController();
        primaryStage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}