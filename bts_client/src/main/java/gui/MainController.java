package gui;

import constants.UserType;
import domain.Bug;
import domain.Message;
import domain.User;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import observer.Observer;
import org.bug_tracker.service.IService;
import utils.AppContext;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainController extends UnicastRemoteObject implements Observer, Serializable {

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
    public void updateBugs(Bug bug) throws RemoteException {

    }

    @Override
    public void updateMessages(Message message) throws RemoteException {

    }

    public void setBugsList(ObservableList<Bug> bugs) {
        this.bugs = bugs;
    }
}
