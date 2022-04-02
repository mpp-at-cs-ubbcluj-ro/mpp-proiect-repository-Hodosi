package competition.persistence;


//public interface ITestTypeRepository <ID, T extends HasId<ID>> extends IRepository<ID, T>{
//}

import competition.model.TestType;

public interface ITestTypeRepository extends IRepository<Integer, TestType> {
}