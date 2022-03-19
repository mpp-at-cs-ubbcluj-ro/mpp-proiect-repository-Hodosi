package service;

import model.HasId;

public interface ITestParticipantRelationService <ID, T extends HasId<ID>>{
    void save(int idTest, int idParticipant);
}
