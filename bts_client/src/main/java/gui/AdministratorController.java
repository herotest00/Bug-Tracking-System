package gui;

import constants.BugStatus;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AdministratorController implements Controller {

    @FXML ListView<User> usersTable;
    @FXML ComboBox<BugStatus> userComboBox;
    @FXML TextField passwordTextField, usernameTextField;
    private User user;

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public void addButtonTriggered(ActionEvent actionEvent) {
    }

    public void deleteButtonTriggered(ActionEvent actionEvent) {
    }

    public void logoutButtonTriggered(ActionEvent actionEvent) {
    }
}
