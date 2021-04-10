package gui;

import domain.User;

public class ProgrammerController implements Controller {

    private User user;

    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
