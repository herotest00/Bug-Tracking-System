package domain;

import java.time.LocalDateTime;

public class Message extends Entity<Long> {

    private final User sender;
    private final Bug bug;
    private final LocalDateTime sendDate;

    public Message(long id, User sender, Bug bug, LocalDateTime sendDate) {
        setId(id);
        this.sender = sender;
        this.bug = bug;
        this.sendDate = sendDate;
    }

    public Message(User sender, Bug bug, LocalDateTime sendDate) {
        this.sender = sender;
        this.bug = bug;
        this.sendDate = sendDate;
    }

    public User getSender() {
        return sender;
    }

    public Bug getBug() {
        return bug;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }
}
