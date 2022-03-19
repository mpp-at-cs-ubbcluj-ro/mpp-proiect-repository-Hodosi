package service;

import model.HasId;

public interface IUserService <ID, T extends HasId<ID>>{
    void login(String username, String password);
    void logout();
    T getCurrentUser();
}
