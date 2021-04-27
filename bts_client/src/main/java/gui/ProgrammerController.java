package gui;

import constants.BugStatus;
import domain.Bug;
import domain.User;
import gui.utils.SceneManager;
import gui.utils.enums.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class ProgrammerController implements Controller {

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

    public void updateButtonTriggered() {
    }

    public void logoutButtonTriggered() {
        mainController.logout();
        SceneManager.getInstance().changeLayout(Scenes.LOGIN);
    }
}
