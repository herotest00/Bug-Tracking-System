package org.bug_tracker.service;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final Map<String, Observer> loggedClients = new ConcurrentHashMap<>();
    private final Map<UserType, Observer> loggedTypesOfUsers = new ConcurrentHashMap<>();


    @Override
    public User addUser(String username, String password, UserType userType) {
        User user= new User(username, password, userType);
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.delete(new User(id));
    }

    @Override
    public ArrayList<User> findAllUsers() {
        return null;
    }

    @Override
    public void reportBug(String name, String description, LocalDateTime reportDate, User tester) {

    }

    @Override
    public void updateBug(long id, LocalDateTime fixDate, User programmer, BugStatus status) {

    }

    @Override
    public void deleteBug(long id) {

    }

    @Override
    public ArrayList<Bug> findAllBugsForTester(long id) {
        return null;
    }

    @Override
    public ArrayList<Bug> findAllBugsForProgrammer(long id) {
        return null;
    }

    @Override
    public ArrayList<Bug> filterBugsByStatusForTester(long id, BugStatus status) {
        return null;
    }

    @Override
    public ArrayList<Bug> filterBugsByStatusForProgrammer(long id, BugStatus status) {
        return null;
    }

    @Override
    public void sendMessage(User sender, Bug bug, LocalDateTime sendDate) {

    }

    @Override
    public ArrayList<Message> findAllMessages(long id) {
        return null;
    }

    @Override
    public User login(String username, String password, Observer client) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null && user.getUserType() != null) {
            loggedClients.put(username, client);
            loggedTypesOfUsers.put(user.getUserType(), client);
        }
        else throw new ServiceException("Invalid username/password!");
        return user;
    }

    @Override
    public void logout(Observer client) {
        loggedClients.values().remove(client);
        loggedTypesOfUsers.values().remove(client);
    }
}
