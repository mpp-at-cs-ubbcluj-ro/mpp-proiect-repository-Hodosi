package competition.persistence;

import model.TestType;

//public interface ITestTypeRepository <ID, T extends HasId<ID>> extends IRepository<ID, T>{
//}

public interface ITestTypeRepository extends repository.IRepository<Integer, TestType> {
}