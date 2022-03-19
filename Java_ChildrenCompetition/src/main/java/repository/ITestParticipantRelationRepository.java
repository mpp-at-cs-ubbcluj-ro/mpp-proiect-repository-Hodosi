package repository;

import model.HasId;

public interface ITestParticipantRelationRepository<ID, T extends HasId<ID>> extends IRepository<ID, T> {

}
