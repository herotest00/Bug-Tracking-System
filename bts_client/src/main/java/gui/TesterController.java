package gui;

import constants.BugStatus;
import domain.Bug;
import domain.User;
import exceptions.ServiceException;
import gui.utils.SceneManager;
import gui.utils.enums.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class TesterController implements Controller {

    @FXML private TableView<Bug> bugsTable;
    @FXML private TableColumn<Bug, String> nameColumn;
    @FXML private TableColumn<Bug, BugStatus> statusColumn;
    @FXML private TextArea descriptionField;
    @FXML private TextField nameField;
    @FXML private ComboBox<BugStatus> filterComboBox, statusComboBox;
    private User user;
    private final MainController mainController = MainController.getMainController();
    ObservableList<Bug> bugs = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        bugsTable.setItems(bugs);
        bugsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        mainController.setBugsList(bugs);
        filterComboBox.setItems(FXCollections.observableArrayList(BugStatus.values()));
        filterComboBox.getSelectionModel().select(BugStatus.ALL);
        statusComboBox.setItems(FXCollections.observableArrayList(BugStatus.OPEN, BugStatus.CLOSED));

        bugsTable.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (event.getClickCount() == 2) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/detailsView.fxml"));
                        Parent root = loader.load();
                        DetailsController controller = loader.getController();
                        controller.setData(bugsTable.getSelectionModel().getSelectedItem());
                        Stage stage = new Stage();
                        stage.setTitle("Details");
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Couldn't open the details window!");
                }
            }
        });
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        mainController.setUser(user);
        bugs.setAll(mainController.findBugsForTester(user.getId()));
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
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void removeButtonTriggered() {
        Bug bug = bugsTable.getSelectionModel().getSelectedItem();
        if (bug == null) {
            new Alert(Alert.AlertType.ERROR, "Select a bug!").show();
            return;
        }
        try {
            mainController.deleteBug(bug.getId());
        } catch (ServiceException e ) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void updateButtonTriggered() {
        Bug bug = bugsTable.getSelectionModel().getSelectedItem();
        BugStatus status = statusComboBox.getValue();
        String errors = "";
        if (bug == null)
            errors += "Select a bug!\n";
        if (status == null)
            errors += "Select a status\n";
        if (bug != null && status == bug.getStatus())
            errors += "Select a new status!\n";
        if (!errors.equals("")) {
            new Alert(Alert.AlertType.ERROR, errors).show();
            return;
        }
        try {
            if (bug.getStatus() != BugStatus.FIXED) {
                new Alert(Alert.AlertType.ERROR, "Can't update the status of an unfixed bug!").show();
            }
            else if (status == BugStatus.OPEN) {
                mainController.updateBug(new Bug(bug.getId(), bug.getName(), bug.getDescription(), bug.getReportDate(), null, bug.getTester(), null, status));
                }
            else if (status == BugStatus.CLOSED) {
                mainController.updateBug(new Bug(bug.getId(), bug.getName(), bug.getDescription(), bug.getReportDate(), bug.getFixDate(), bug.getTester(), bug.getProgrammer(), status));
            }
        } catch (ServiceException e ) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void filterStatusSelected() {
        BugStatus bugStatus = filterComboBox.getValue();
        if (bugStatus != null )
            if (bugStatus != BugStatus.ALL)
                bugs.setAll(mainController.filterBugsByStatusForTester(user.getId(), bugStatus));
            else bugs.setAll(mainController.findBugsForTester(user.getId()));
    }
}
