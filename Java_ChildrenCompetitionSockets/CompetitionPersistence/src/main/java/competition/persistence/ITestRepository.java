package competition.persistence;


//public interface ITestRepository<ID, T extends HasId<ID>> extends IRepository<ID, T>{
//    Iterable<T> findAllTestsForParticipant(int id);
//}

import competition.model.Test;

public interface ITestRepository extends IRepository<Integer, Test>{
    Iterable<Test> findAllTestsForParticipant(int id);
}