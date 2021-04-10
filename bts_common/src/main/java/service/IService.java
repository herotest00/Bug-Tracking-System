package service;

import constants.BugStatus;
import constants.UserType;
import domain.Bug;
import domain.Message;
import domain.User;
import observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IService {

    void addUser(String username, String password, UserType type);

    void deleteUser(long id);

    ArrayList<User> findAllUsers();

    void reportBug(String name, String description, LocalDateTime reportDate, User tester);

    //if programmer = null, update status of bug, else update programmer and status = means he tries to fix it
    //if status == fixed, update status and fixDate (check in controller that programmer is the one that the bug is assigned to)
    void updateBug(long id, LocalDateTime fixDate, User programmer, BugStatus status);

    //validate in controller if id_tester == id who wants to delete the bug
    void deleteBug(long id);

    //tester can see his fixed, opened, assigned bugs
    // duplicate too (but can't change status for them)
    ArrayList<Bug> findAllBugsForTester(long id);

    //programmer can see his fixed, opened, assigned bugs
    ArrayList<Bug> findAllBugsForProgrammer(long id);

    ArrayList<Bug> filterBugsByStatusForTester(long id, BugStatus status);

    ArrayList<Bug> filterBugsByStatusForProgrammer(long id, BugStatus status);

    void sendMessage(User sender, Bug bug, LocalDateTime sendDate);

    //chronological order
    ArrayList<Message> findAllMessages(long id);

    User login(String username, String password, Observer client);

    void logout(Observer client);
}
