package gui;

import domain.User;
import domain.validator.ValidationException;
import exceptions.RepoException;
import exceptions.ServiceException;
import gui.utils.SceneManager;
import gui.utils.enums.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Controller {

    @FXML TextField usernameTextField;
    @FXML PasswordField passwordTextField;

    private User user;
    private final MainController mainController;

    public LoginController() {
        mainController = MainController.getMainController();
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public void loginButtonTriggered() {
        try {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            if (username.equals("") || password.equals("")) {
                new Alert(Alert.AlertType.ERROR, "Invalid username/password!").show();
                return;
            }
            User user = mainController.login(username, password);
            SceneManager.getInstance().changeLayout(Scenes.MAIN, user);
            clearFields();
        } catch (ValidationException | RepoException | ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        usernameTextField.clear();
        passwordTextField.clear();
    }
}
