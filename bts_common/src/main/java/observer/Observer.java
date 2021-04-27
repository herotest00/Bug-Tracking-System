package observer;

import constants.ActionType;
import domain.Bug;
import domain.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {

    void updateBugs(Bug bug, ActionType actionType) throws RemoteException;

    void updateMessages(Message message) throws RemoteException;
}
