package domain;

import constants.BugStatus;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity(name = "Bug")
@Table(name = "Bugs", indexes = { @Index(name = "IDX_BugStatus", columnList = "status"), @Index(name = "IDX_BugProgrammer", columnList = "tester_id") })
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

    @Column(name = "fix_date", nullable = false)
    public LocalDateTime getFixDate() {
        return fixDate;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tester_id", nullable = false)
    public User getTester() {
        return tester;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "programmer_id")
    public User getProgrammer() {
        return programmer;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public BugStatus getStatus() {
        return status;
    }
}
