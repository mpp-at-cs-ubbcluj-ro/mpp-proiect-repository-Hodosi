package repository;

import model.User;

public class CurrentUser {
    private final User user;

    public CurrentUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
