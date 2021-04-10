package gui.utils;

import domain.User;
import gui.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;


class SceneInitializer {

    private final static SceneInitializer sceneInitializer = new SceneInitializer();

    public static SceneInitializer getInstance() {
        return sceneInitializer;
    }

    public Pane loadLogin() throws IOException {
        FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("/view/loginView.fxml"));
        return fxmlLoader.<AnchorPane>load();
    }

    public Parent loadMain(User user) throws IOException {
        FXMLLoader loader = switch (user.getUserType()) {
            case ADMINISTRATOR -> new FXMLLoader(getClass().getResource("/view/administratorView.fxml"));
            case TESTER -> new FXMLLoader(getClass().getResource("/view/testerView.fxml"));
            case PROGRAMMER -> new FXMLLoader(getClass().getResource("/view/programmerView.fxml"));
        };
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setUser(user);
        return root;
    }
}
