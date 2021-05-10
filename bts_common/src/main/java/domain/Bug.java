package domain;

import constants.BugStatus;
import constants.DateConstants;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity(name = "Bug")
@Table(name = "Bugs")
public class Bug extends domain.Entity<Long> {

    private String name;
    private String description;
    private LocalDateTime reportDate;
    private LocalDateTime fixDate;
    private User tester;
    private User programmer;
    private BugStatus status;

    public Bug() {
    }

    public Bug(long id) {
        setId(id);
    }

    public Bug(long id, String name, String description, LocalDateTime reportDate, LocalDateTime fixDate, User tester, User programmer, BugStatus status) {
        setId(id);
        this.name = name;
        this.description = description;
        this.reportDate = reportDate;
        this.fixDate = fixDate;
        this.tester = tester;
        this.programmer = programmer;
        this.status = status;
    }

    public Bug(long id, String name, String description, User tester, User programmer, BugStatus status) {
        setId(id);
        this.name = name;
        this.description = description;
        this.tester = tester;
        this.programmer = programmer;
        this.status = status;
    }

    public Bug(String name, String description, LocalDateTime reportDate, User tester) {
        this.name = name;
        this.description = description;
        this.reportDate = reportDate;
        this.tester = tester;
    }

    public Bug(String name, String description, LocalDateTime reportDate, User tester, BugStatus status) {
        this.name = name;
        this.description = description;
        this.reportDate = reportDate;
        this.tester = tester;
        this.status = status;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public void setFixDate(LocalDateTime fixDate) {
        this.fixDate = fixDate;
    }

    public void setTester(User tester) {
        this.tester = tester;
    }

    public void setProgrammer(User programmer) {
        this.programmer = programmer;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(name = "report_date", nullable = false)
    public LocalDateTime getReportDate() {
        return reportDate;
    }

    @Column(name = "fix_date")
    public LocalDateTime getFixDate() {
        return fixDate;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tester_id", nullable = false)
    public User getTester() {
        return tester;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "programmer_id")
    public User getProgrammer() {
        return programmer;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public BugStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "Bug{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reportDate=" + reportDate +
                ", fixDate=" + fixDate +
                ", tester=" + tester +
                ", programmer=" + programmer +
                ", status=" + status +
                '}';
    }
}