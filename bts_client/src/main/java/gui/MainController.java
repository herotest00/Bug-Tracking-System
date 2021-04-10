package gui;

import domain.Bug;
import domain.Message;
import domain.User;
import javafx.scene.control.Alert;
import observer.Observer;
import service.IService;
import utils.AppContext;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainController extends UnicastRemoteObject implements Observer, Serializable {

    IService service;
    private static MainController mainController = null;

    private MainController() throws RemoteException {
        service = (IService) AppContext.getApplicationContext().getBean("service");
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

    @Override
    public void updateBugs(Bug bug) throws RemoteException {

    }

    @Override
    public void updateMessages(Message message) throws RemoteException {

    }
}
