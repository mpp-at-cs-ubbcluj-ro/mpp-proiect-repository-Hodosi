package repository;

import model.Test;

public class TestRepository extends AbstractRepository<Integer, Test> {
    public TestRepository(Validator<Test> validator){
        super(validator);
    }
}
