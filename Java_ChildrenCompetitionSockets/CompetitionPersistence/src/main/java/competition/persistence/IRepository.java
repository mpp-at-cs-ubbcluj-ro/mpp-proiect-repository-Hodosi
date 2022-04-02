package competition.persistence;


import competition.model.HasId;

public interface IRepository <ID, T extends HasId<ID>> {
    int size();
    void save(T entity);
    void delete(ID id);
    void update(ID id, T entity);
    T findOne(ID id);
    Iterable<T> findAll();
}
