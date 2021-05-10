package gui;

import constants.BugStatus;
import domain.Bug;
import domain.User;
import exceptions.ServiceException;
import gui.utils.SceneInitializer;
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
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.time.LocalDateTime;

public class ProgrammerController implements Controller {

    @FXML private TableView<Bug> bugsTable;
    @FXML private TableColumn<Bug, String> nameColumn;
    @FXML private TableColumn<Bug, BugStatus> statusColumn;
    @FXML private ComboBox<BugStatus> filterComboBox, statusComboBox;
    private User user;
    private final MainController mainController = MainController.getMainController();
    ObservableList<Bug> allBugs = FXCollections.observableArrayList();
    ObservableList<Bug> filteredBugs = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        bugsTable.setItems(allBugs);
        bugsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        mainController.setBugsList(allBugs);
        filterComboBox.setItems(FXCollections.observableArrayList(BugStatus.ALL, BugStatus.OPEN, BugStatus.ASSIGNED, BugStatus.FIXED, BugStatus.CLOSED));
        filterComboBox.getSelectionModel().select(BugStatus.ALL);
        statusComboBox.setItems(FXCollections.observableArrayList(BugStatus.ASSIGNED, BugStatus.FIXED, BugStatus.DUPLICATE));
        nameColumn.setStyle("-fx-alignment: CENTER;");
        statusColumn.setStyle("-fx-alignment: CENTER; ");

        bugsTable.setOnMouseClicked(event -> {
            try {
                if (event.getClickCount() == 2) {
                    Stage stage = new Stage();
                    stage.setTitle("Details");
                    stage.setScene(new Scene(SceneInitializer.getInstance().loadDetails(bugsTable.getSelectionModel().getSelectedItem())));
                    stage.show();
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Couldn't open the details window!").show();
            }
        });
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        mainController.setUser(user);
        allBugs.setAll(mainController.findBugsForProgrammer(user.getId()));
        mainController.setBugsList(allBugs);
    }

    public void updateButtonTriggered() {
        Bug bug = bugsTable.getSelectionModel().getSelectedItem();
        System.out.println(bug);
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
            if (bug.getStatus() == BugStatus.CLOSED) {
                new Alert(Alert.AlertType.ERROR, "Can't change the status of an already fixed bug!").show();
            }
            else if (status == BugStatus.DUPLICATE) {
                mainController.updateBug(new Bug(bug.getId(), bug.getName(), bug.getDescription(), bug.getReportDate(), null, bug.getTester(), null, status));
                statusComboBox.getSelectionModel().clearSelection();
            }
            else if (status == BugStatus.FIXED) {
                if (bug.getStatus() != BugStatus.ASSIGNED) {
                    new Alert(Alert.AlertType.ERROR, "Can't mark an unassigned bug as fixed!").show();
                }
                else {
                    mainController.updateBug(new Bug(bug.getId(), bug.getName(), bug.getDescription(), bug.getReportDate(), LocalDateTime.now(), bug.getTester(), bug.getProgrammer(), status));
                    statusComboBox.getSelectionModel().clearSelection();
                }
            }
            else if (status == BugStatus.ASSIGNED) {
                mainController.updateBug(new Bug(bug.getId(), bug.getName(), bug.getDescription(), bug.getReportDate(), null, bug.getTester(), user, status));
                statusComboBox.getSelectionModel().clearSelection();
            }
        } catch (ServiceException e ) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void logoutButtonTriggered() {
        mainController.logout();
        SceneManager.getInstance().changeLayout(Scenes.LOGIN);
    }

    public void filterStatusSelected() {
        BugStatus bugStatus = filterComboBox.getValue();
        if (bugStatus != null )
            if (bugStatus != BugStatus.ALL) {
                filteredBugs.setAll(mainController.filterBugsByStatusForProgrammer(user.getId(), bugStatus));
                bugsTable.setItems(filteredBugs);
            }
            else {
                bugsTable.setItems(allBugs);
            }
    }

    public void openChatButtonTriggered() throws IOException {
        Bug bug = bugsTable.getSelectionModel().getSelectedItem();
        if (bug != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/chatView.fxml"));
                Parent root = loader.load();
                ChatController controller = loader.getController();
                controller.setUser(user, bug);
                Stage stage = new Stage();
                stage.setTitle(bug.getName());
                stage.setScene(new Scene(root));
                stage.show();
                stage.setOnCloseRequest(event -> controller.closeHandler());
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Couldn't open chat window!").show();
            }
        }
    }
}
