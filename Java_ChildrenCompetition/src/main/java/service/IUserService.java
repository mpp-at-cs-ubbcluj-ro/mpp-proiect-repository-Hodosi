package service;

import model.User;

//public interface IUserService <ID, T extends HasId<ID>>{
//    void login(String username, String password);
//    void logout();
//    T getCurrentUser();
//}

public interface IUserService {
    void login(String username, String password);
    void logout();
    User getCurrentUser();
}
