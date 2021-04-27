package gui;

import constants.BugStatus;
import domain.Bug;
import domain.User;
import exceptions.ServiceException;
import gui.utils.SceneManager;
import gui.utils.enums.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TesterController implements Controller {

    @FXML private TextArea descriptionField;
    @FXML private TextField nameField;
    @FXML private ListView<Bug> bugsList;
    @FXML private ComboBox<BugStatus> filterComboBox, statusComboBox;
    private User user;
    private MainController mainController = MainController.getMainController();
    ObservableList<Bug> bugs = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        bugsList.setItems(bugs);
        bugsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        mainController.setBugsList(bugs);
        filterComboBox.setItems(FXCollections.observableArrayList(BugStatus.values()));
        statusComboBox.setItems(FXCollections.observableArrayList(BugStatus.values()));
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        bugs.setAll(mainController.findBugsForProgrammer(user.getId()));
        mainController.setBugsList(bugs);
    }

    public void logoutButtonTriggered() {
        mainController.logout();
        SceneManager.getInstance().changeLayout(Scenes.LOGIN);
    }

    public void addButtonTriggered() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String errors = "";
        if (name.equals(""))
            errors += "Name can't be empty!\n";
        if (description.equals(""))
            errors += "Description can't be empty!\n";
        if (!errors.equals("")) {
            new Alert(Alert.AlertType.ERROR, errors).show();
            return;
        }
        try {
            mainController.reportBug(user, name, description);
        } catch (ServiceException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return;
        }

    }

    public void removeButtonTriggered() {
        Bug bug = bugsList.getSelectionModel().getSelectedItem();
        if (bug == null) {
            new Alert(Alert.AlertType.ERROR, "Select a bug!");
            return;
        }
        try {
            mainController.deleteBug(bug.getId());
        } catch (ServiceException e ) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return;
        }
    }

    public void updateButtonTriggered() {
    }
}
