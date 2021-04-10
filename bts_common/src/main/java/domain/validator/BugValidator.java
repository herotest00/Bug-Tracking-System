package domain.validator;

import domain.Bug;

import java.time.LocalDateTime;

public class BugValidator implements Validator<Bug> {

    @Override
    public void validate(Bug entity) {
        String errors = "";
        if (entity.getName() == null || entity.getName().equals(""))
            errors = errors.concat("Name can't be emtpy!\n");
        if (entity.getDescription() == null || entity.getDescription().equals(""))
            errors = errors.concat("Description can't be empty!\n");
        if (entity.getReportDate() == null || LocalDateTime.now().compareTo(entity.getReportDate()) < 0)
            errors = errors.concat("Invalid report date!\n");
        if (entity.getTester() == null)
            errors = errors.concat("Invalid tester who reported the bug!\n");
        if (entity.getStatus() == null)
            errors = errors.concat("Invalid bug status!\n");
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
