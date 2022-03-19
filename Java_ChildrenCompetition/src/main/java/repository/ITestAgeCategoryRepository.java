package repository;

import model.HasId;

public interface ITestAgeCategoryRepository <ID, T extends HasId<ID>> extends IRepository<ID, T>{
}
