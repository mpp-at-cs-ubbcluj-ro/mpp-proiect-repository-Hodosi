package repository;

import model.HasId;

public interface IUserRepository <ID, T extends HasId<ID>> extends IRepository<ID, T>{
    void setCurrentUser(T entity);
    T getCurrentUser();
    T findByUsername(String username);
}
