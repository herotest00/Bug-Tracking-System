package domain;

import constants.BugStatus;

import java.time.LocalDateTime;

public class Bug extends Entity<Long> {

    private final String name;
    private final String description;
    private LocalDateTime reportDate;
    private LocalDateTime fixDate;
    private final User tester;
    private User programmer;
    private BugStatus status;

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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public LocalDateTime getFixDate() {
        return fixDate;
    }

    public User getTester() {
        return tester;
    }

    public User getProgrammer() {
        return programmer;
    }

    public BugStatus getStatus() {
        return status;
    }
}
