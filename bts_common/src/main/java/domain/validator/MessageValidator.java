package domain.validator;

import domain.Message;

import java.time.LocalDateTime;

public class MessageValidator implements Validator<Message> {

    @Override
    public void validate(Message entity) {
        String errors = "";
        if (entity.getSender() == null)
            errors = errors.concat("The message must have a sender!\n");
        if (entity.getBug() == null)
            errors = errors.concat("The message must be sent within a bug!\n");
        if (entity.getSendDate() == null || LocalDateTime.now().compareTo(entity.getSendDate()) < 0)
            errors = errors.concat("Invalid send date!\n");
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
