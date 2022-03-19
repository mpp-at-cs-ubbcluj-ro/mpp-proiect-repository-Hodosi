package service;

import model.HasId;
import model.TestDTO;

import java.util.List;

public interface ITestService  <ID, T extends HasId<ID>>{
    List<TestDTO> findAllTestDTOs();
}
