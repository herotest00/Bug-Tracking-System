package domain.validator;

import domain.User;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User entity) {
        String errors = "";
        if (entity.getUsername() == null || entity.getUsername().isEmpty())
            errors = errors.concat("Username can't be empty!\n");
        if (entity.getPassword() == null || entity.getPassword().isEmpty())
            errors = errors.concat("Password can't be empty!\n");
        if (entity.getUserType() == null)
            errors = errors.concat("Invalid type of user!\n");
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
