package repository;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepository<ID, T extends HasId<ID>> implements IRepository<ID, T> {
    protected Map<ID, T> entities;
    protected Validator<T> validator;

    public AbstractRepository(Validator<T> validator) {
        this.entities = new HashMap<>();
        this.validator = validator;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void update(ID id, T entity) { }

    @Override
    public void save(T entity) { }

    @Override
    public void delete(ID id) { }

    @Override
    public T findOne(ID id) {
        return null;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }
}
