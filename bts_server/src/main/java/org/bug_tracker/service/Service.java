package org.bug_tracker.service;

import constants.ActionType;
import constants.BugStatus;
import constants.UserType;
import domain.Bug;
import domain.Message;
import domain.User;
import exceptions.ServiceException;
import observer.Observer;
import org.bug_tracker.repo.BugRepository;
import org.bug_tracker.repo.MessageRepository;
import org.bug_tracker.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@org.springframework.stereotype.Service
public class Service implements IService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BugRepository bugRepository;
    @Autowired
    private MessageRepository messageRepository;
    private final Map<User, Observer> loggedClients = new ConcurrentHashMap<>();


    @Override
    public User addUser(String username, String password, UserType userType) {
        User user = new User(username, password, userType);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ServiceException("Username already in use!");
        }
        return user;
    }

    @Override
    public void deleteUser(long id) {
        try {
            userRepository.delete(new User(id));
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void reportBug(User tester, String name, String description, LocalDateTime reportDate) {
        Bug bug = new Bug(name, description, reportDate, tester, BugStatus.OPEN);
        try {
            bugRepository.save(bug);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        updateBugsForClients(bug, ActionType.ADD);
    }

    private void updateBugsForClients(Bug bug, ActionType actionType) {
        for (Map.Entry<User, Observer> entry : loggedClients.entrySet()) {
            User user = entry.getKey();
            if (user.getUserType() != UserType.ADMINISTRATOR) {
                Observer client = entry.getValue();
                try {
                    client.updateBugs(bug, actionType);
                } catch (RemoteException e) {
                    System.out.println("Error updating bug for " + user.getUsername());
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void updateBug(long id, LocalDateTime fixDate, User programmer, BugStatus status) {

    }

    @Override
    public void deleteBug(long id) {
        Bug bug = new Bug(id);
        try {
            bugRepository.delete(bug);
            updateBugsForClients(bug, ActionType.REMOVE);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Bug> findAllBugsForTester(long id) {
        return bugRepository.findBugsByTester_Id(id);
    }

    @Override
    public List<Bug> findAllBugsForProgrammer(long id) {
        return bugRepository.findBugsByProgrammer_Id(id);
    }

    @Override
    public List<Bug> filterBugsByStatusForTester(long id, BugStatus status) {
        return bugRepository.findBugsByStatusAndTester_Id(status, id);
    }

    @Override
    public List<Bug> filterBugsByStatusForProgrammer(long id, BugStatus status) {
        return bugRepository.findBugsByStatusAndProgrammer_Id(status, id);
    }

    @Override
    public void sendMessage(User sender, Bug bug, LocalDateTime sendDate) {

    }

    @Override
    public List<Message> findAllMessages(long id) {
        return null;
    }

    @Override
    public User login(String username, String password, Observer client) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null && user.getUserType() != null) {
            loggedClients.put(user, client);
        }
        else throw new ServiceException("Invalid username/password!");
        return user;
    }

    @Override
    public void logout(Observer client) {
        loggedClients.values().remove(client);
    }
}
