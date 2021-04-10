import gui.utils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import utils.AppContext;

import java.io.IOException;

public class MainFX extends Application {

    @Override
    public void start(Stage loginStage) {

        AppContext.setApplicationContext(new ClassPathXmlApplicationContext("xml/bts.xml"));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/loginView.fxml"));

        try {
            AnchorPane root = fxmlLoader.load();
            Scene mainScene = new Scene(root);
            SceneManager.getInstance().setScene(mainScene);
            loginStage.setResizable(false);
            loginStage.setScene(mainScene);
            loginStage.setTitle("Bug tracking system");
            loginStage.show();
        } catch (IOException ignored) {

        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}