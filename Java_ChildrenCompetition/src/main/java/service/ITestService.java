package service;

import model.TestDTO;

import java.util.List;

//public interface ITestService  <ID, T extends HasId<ID>>{
//    List<TestDTO> findAllTestDTOs();
//}

public interface ITestService {
    List<TestDTO> findAllTestDTOs();
}
