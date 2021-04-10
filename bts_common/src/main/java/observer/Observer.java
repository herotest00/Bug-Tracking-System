package observer;

import domain.Bug;
import domain.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {

    void updateBugs(Bug bug) throws RemoteException;

    void updateMessages(Message message) throws RemoteException;
}
