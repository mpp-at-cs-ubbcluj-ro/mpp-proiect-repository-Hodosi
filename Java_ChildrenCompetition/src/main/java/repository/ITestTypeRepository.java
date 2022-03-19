package repository;

import model.HasId;

public interface ITestTypeRepository <ID, T extends HasId<ID>> extends IRepository<ID, T>{
}
