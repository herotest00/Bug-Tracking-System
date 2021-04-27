package domain;

import javax.persistence.*;
import javax.persistence.Entity;

import java.time.LocalDateTime;

@Entity(name = "Message")
@Table(name = "Messages", indexes = { @Index(name = "IDX_Message", columnList = "bug")})
public class Message extends domain.Entity<Long> {

    private String messsage;
    private User sender;
    private Bug bug;
    private LocalDateTime sendDate;

    public Message() {
    }

    public Message(long id, String message, User sender, Bug bug, LocalDateTime sendDate) {
        setId(id);
        this.messsage = message;
        this.sender = sender;
        this.bug = bug;
        this.sendDate = sendDate;
    }

    public Message(String message, User sender, Bug bug, LocalDateTime sendDate) {
        this.messsage = message;
        this.sender = sender;
        this.bug = bug;
        this.sendDate = sendDate;
    }

    @Override
    @Id
    @GeneratedValue
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long aLong) {
        super.setId(aLong);
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sender", nullable = false)
    public User getSender() {
        return sender;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_bug", nullable = false)
    public Bug getBug() {
        return bug;
    }

    @Column(name = "send_date", nullable = false)
    public LocalDateTime getSendDate() {
        return sendDate;
    }

    @Column(name = "message", nullable = false)
    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }
}
