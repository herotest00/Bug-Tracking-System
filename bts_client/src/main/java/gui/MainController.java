package gui;

import constants.ActionType;
import constants.BugStatus;
import constants.UserType;
import domain.Bug;
import domain.Message;
import domain.User;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import observer.Observer;
import org.bug_tracker.service.IService;
import utils.AppContext;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;

public class MainController extends UnicastRemoteObject implements Observer, Serializable {

    private User user;
    private final IService service;
    private static MainController mainController = null;
    private ObservableList<Bug> bugs;

    private MainController() throws RemoteException {
        service = (IService) AppContext.getApplicationContext().getBean("org.bug_tracker.service");
    }

    public static MainController getMainController() {
        try {
            if (mainController == null) {
                mainController = new MainController();
            }
        } catch (RemoteException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return mainController;
    }

    public User login(String username, String password) {
        return service.login(username, password, this);
    }

    public void logout() {
        service.logout(this);
        bugs = null;
    }

    public User addUser(String username, String password, UserType userType) {
        return service.addUser(username, password, userType);
    }

    public void deleteUser(long id) {
        service.deleteUser(id);
    }

    @Override
    public void updateBugs(Bug bug, ActionType actionType) throws RemoteException {
        switch (actionType) {
            case ADD -> Platform.runLater(() -> {
                bugs.remove(bug);
                bugs.add(bug);
            });
            case UPDATE -> Platform.runLater(() -> {
                var poz = bugs.indexOf(bug);
                if (poz != -1)
                    bugs.set(poz, bug);
            });
            case REMOVE -> Platform.runLater(() -> bugs.remove(bug));
        }
    }

    @Override
    public void updateMessages(Message message) throws RemoteException {

    }

    public void setBugsList(ObservableList<Bug> bugs) {
        this.bugs = bugs;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> findAllUsers() {
        return service.findAllUsers();
    }

    public List<Bug> findBugsForProgrammer(long id) {
        return service.findAllBugsForProgrammer(id);
    }

    public void reportBug(User user, String name, String description) {
        service.reportBug(user, name, description, LocalDateTime.now());
    }

    public void deleteBug(long id) {
        service.deleteBug(id);
    }

    public List<Bug> filterBugsByStatusForProgrammer(long id, BugStatus bugStatus) {
        return service.filterBugsByStatusForProgrammer(id, bugStatus);
    }

    public List<Bug> filterBugsByStatusForTester(Long id, BugStatus bugStatus) {
        return service.filterBugsByStatusForTester(id, bugStatus);
    }

    public void updateBug(Bug bug) {
        service.updateBug(bug);
    }

    public List<Bug> findBugsForTester(Long id) {
        return service.findAllBugsForTester(id);
    }
}
