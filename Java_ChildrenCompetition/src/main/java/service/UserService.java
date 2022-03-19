package service;

import exception.WrongPasswordException;
import exception.WrongUsernameException;
import model.User;
import repository.IUserRepository;

public class UserService implements IUserService<Integer, User> {
    public final IUserRepository<Integer, User> userRepository;

    public UserService(IUserRepository<Integer, User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            if(!user.getPassword().equals(password)){
                throw new WrongPasswordException();
            }
            userRepository.setCurrentUser(user);
        }
        else {
            throw new WrongUsernameException();
        }
    }

    @Override
    public void logout() {
        userRepository.setCurrentUser(null);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }
}
