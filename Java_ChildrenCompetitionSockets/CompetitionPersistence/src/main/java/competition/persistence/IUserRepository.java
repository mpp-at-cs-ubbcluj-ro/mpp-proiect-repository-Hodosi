package competition.persistence;


//public interface IUserRepository <ID, T extends HasId<ID>> extends IRepository<ID, T>{
//    void setCurrentUser(T entity);
//    T getCurrentUser();
//    T findByUsername(String username);
//}

import competition.model.User;

public interface IUserRepository extends IRepository<Integer, User> {
    void setCurrentUser(User entity);
    User getCurrentUser();
    User findByUsername(String username);
}