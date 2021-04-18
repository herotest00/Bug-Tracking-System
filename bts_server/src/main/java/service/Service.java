package service;

import constants.BugStatus;
import constants.UserType;
import domain.Bug;
import domain.Message;
import domain.User;
import observer.Observer;
import repo.BugRepository;
import repo.MessageRepository;
import repo.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Service implements IService {

    private final UserRepository userRepository;
    private final BugRepository bugRepository;
    private final MessageRepository messageRepository;
    private final Map<String, Observer> loggedClients = new ConcurrentHashMap<>();
    private final Map<UserType, Observer> loggedTypesOfUsers = new ConcurrentHashMap<>();

    public Service(UserRepository userRepository, BugRepository bugRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.bugRepository = bugRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void addUser(String username, String password, UserType type) {

    }

    @Override
    public void deleteUser(long id) {

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
        User user = userRepository.login(new User(username, password));
        if (user != null && user.getUserType() != null) {
            loggedClients.put(username, client);
            loggedTypesOfUsers.put(user.getUserType(), client);
        }
        return user;
    }

    @Override
    public void logout(Observer client) {
        while (loggedClients.values().remove(client));
        while (loggedTypesOfUsers.values().remove(client));
    }
}
