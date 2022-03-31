package competition.persistence;

import model.User;

//public interface IUserRepository <ID, T extends HasId<ID>> extends IRepository<ID, T>{
//    void setCurrentUser(T entity);
//    T getCurrentUser();
//    T findByUsername(String username);
//}

public interface IUserRepository extends repository.IRepository<Integer, User> {
    void setCurrentUser(User entity);
    User getCurrentUser();
    User findByUsername(String username);
}