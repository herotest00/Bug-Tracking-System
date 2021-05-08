package gui;

import domain.Bug;
import domain.Message;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class ChatController {

    @FXML public ListView<Message> messagesListView;
    @FXML public TextArea messageTextArea;
    private User user;
    private Bug bug;
    private final MainController mainController = MainController.getMainController();
    public ObservableList<Message> messages = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        messagesListView.setItems(messages);
    }

    public void setUser(User user, Bug bug) {
        this.user = user;
        this.bug = bug;
        messages.setAll(mainController.findMessagesForBug(bug.getId()));
        mainController.setMessagesList(bug, this);
    }

    public void sendButtonTriggered() {
        String text = messageTextArea.getText();
        text = text.replaceAll("\\s+$", "");
        if (text.equals("")) {
            new Alert(Alert.AlertType.ERROR, "Empty message!").show();
            return;
        }
        mainController.sendMessage(text, user, bug);
    }
}
