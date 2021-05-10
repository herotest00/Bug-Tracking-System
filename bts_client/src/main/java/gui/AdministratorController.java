package gui;

import constants.UserType;
import domain.User;
import exceptions.ServiceException;
import gui.utils.SceneManager;
import gui.utils.enums.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdministratorController implements Controller {

    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, UserType> userTypeColumn;
    private final MainController mainController = MainController.getMainController();
    private final ObservableList<User> users = FXCollections.observableArrayList();
    @FXML private ComboBox<UserType> userTypeComboBox;
    @FXML private TextField passwordTextField, usernameTextField;
    private User user;

    @FXML
    void initialize() {
        usersTable.setItems(users);
        usersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        userTypeComboBox.setItems(FXCollections.observableArrayList(UserType.TESTER, UserType.PROGRAMMER));
        usernameColumn.setStyle("-fx-alignment: CENTER;");
        userTypeColumn.setStyle("-fx-alignment: CENTER; ");
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        mainController.setUser(user);
        users.setAll(mainController.findAllUsers());
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
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void deleteButtonTriggered() {
        User user = usersTable.getSelectionModel().getSelectedItem();
        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Select a user!");
            return;
        }
        try {
            mainController.deleteUser(user.getId());
            users.remove(user);
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
