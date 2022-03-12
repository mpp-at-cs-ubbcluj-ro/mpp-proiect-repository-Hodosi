package repository;

import model.HasId;

public interface ITestRepository<ID, T extends HasId<ID>> extends IRepository<ID, T>{

}
