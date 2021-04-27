package gui;

import constants.BugStatus;
import constants.UserType;
import domain.User;
import exceptions.RepoException;
import gui.utils.SceneManager;
import gui.utils.enums.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdministratorController implements Controller {

    private MainController mainController = MainController.getMainController();
    private ObservableList<User> users = FXCollections.observableArrayList();
    @FXML private ListView<User> usersList;
    @FXML private ComboBox<UserType> userTypeComboBox;
    @FXML private TextField passwordTextField, usernameTextField;
    private User user;

    @FXML
    void initialize() {
        userTypeComboBox.setItems(FXCollections.observableArrayList(UserType.values()));
        usersList.setItems(users);
        usersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public void addButtonTriggered() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        UserType userType = userTypeComboBox.getValue();
        String errors = "";
        if (username.equals(""))
            errors += "Username can't be empty!\n";
        if (password.equals(""))
            errors += "Password can't be empty!\n";
        if (userType == null)
            errors += "User type can't be empty!\n";
        if (!errors.equals("")) {
            new Alert(Alert.AlertType.ERROR, errors).show();
            return;
        }
        try {
            User user = mainController.addUser(username, password, userType);
            users.add(user);
            clearFields();
        } catch (RepoException e) {
            System.out.println("ASDA");
        }
    }

    public void deleteButtonTriggered() {
        User user = usersList.getSelectionModel().getSelectedItem();
        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Select a user!");
            return;
        }
        try {
            mainController.deleteUser(user.getId());
            users.remove(user);
        } catch (RepoException e) {
            System.out.println("ASDA");
        }
    }

    public void clearFields() {
        usernameTextField.clear();
        passwordTextField.clear();
        userTypeComboBox.setButtonCell(null);
    }

    public void logoutButtonTriggered() {
        mainController.logout();
        SceneManager.getInstance().changeLayout(Scenes.LOGIN);
    }
}
