package codes.main;

import codes.classes.Bank;
import codes.utils.Utils;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.util.Objects;

public class Main extends Application {
    public static boolean isUser;
    public static String nid;
    public static Bank bank;
    public static Stage primaryStage;
    public static Scene scene;
    public static Parent root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws URISyntaxException {
        // initial
        isUser = true;
        Utils.checkDB();    // Create DB if not exist
        bank = Utils.readBankFromFile(Configs.db);   // Read DB
        primaryStage = stage;

        // Window Settings
        primaryStage.setTitle(Configs.title + " " + Configs.version);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(Configs.icon)).toURI().toString()));
        Utils.changeScene(Configs.homePage);

        // Window Style
        //primaryStage.initStyle(StageStyle.TRANSPARENT);

        // Show
        primaryStage.show();
    }
}