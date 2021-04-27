package org.bug_tracker.service;

import constants.BugStatus;
import constants.UserType;
import domain.Bug;
import domain.Message;
import domain.User;
import observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface IService {

    User addUser(String username, String password, UserType type);

    void deleteUser(long id);

    List<User> findAllUsers();

    void reportBug(User tester, String name, String description, LocalDateTime reportDate);

    //if programmer = null, update status of bug, else update programmer and status = means he tries to fix it
    //if status == fixed, update status and fixDate (check in controller that programmer is the one that the bug is assigned to)
    void updateBug(long id, LocalDateTime fixDate, User programmer, BugStatus status);

    //validate in controller if id_tester == id who wants to delete the bug
    void deleteBug(long id);

    //tester can see his fixed, opened, assigned bugs
    // duplicate too (but can't change status for them)
    List<Bug> findAllBugsForTester(long id);

    //programmer can see his fixed, opened, assigned bugs
    List<Bug> findAllBugsForProgrammer(long id);

    List<Bug> filterBugsByStatusForTester(long id, BugStatus status);

    List<Bug> filterBugsByStatusForProgrammer(long id, BugStatus status);

    void sendMessage(User sender, Bug bug, LocalDateTime sendDate);

    //chronological order
    List<Message> findAllMessages(long id);

    User login(String username, String password, Observer client);

    void logout(Observer client);
}
