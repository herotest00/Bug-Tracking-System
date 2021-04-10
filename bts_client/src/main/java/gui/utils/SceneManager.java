package gui.utils;

import domain.User;
import gui.utils.enums.Scenes;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.HashMap;


public class SceneManager {

    private Scene mainScene;
    private final HashMap<Scenes, Parent> roots = new HashMap<>();
    private static final SceneManager sceneManager = new SceneManager();
    SceneInitializer sceneInitializer = SceneInitializer.getInstance();

    private SceneManager() {
        try {
            roots.putIfAbsent(Scenes.LOGIN, sceneInitializer.loadLogin());
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void setScene(Scene scene) {
        this.mainScene = scene;
    }

    public static SceneManager getInstance() {
        return sceneManager;
    }

    public void changeLayout(Scenes name) {
        if (name == Scenes.LOGIN) {
            mainScene.setRoot(roots.get(Scenes.LOGIN));
        }
    }

    public void changeLayout(Scenes name, User volunteer) {
        try {
            if (name == Scenes.MAIN) {
                mainScene.setRoot(SceneInitializer.getInstance().loadMain(volunteer));
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public Parent getRoot(Scenes scene) {
        return roots.get(scene);
    }
}
