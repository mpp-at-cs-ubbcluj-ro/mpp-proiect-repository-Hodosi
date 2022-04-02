package competition.persistence;


//public interface ITestParticipantRelationRepository<ID, T extends HasId<ID>> extends IRepository<ID, T> {
//
//}

import competition.model.TestParticipantRelation;
import competition.model.Tuple;

public interface ITestParticipantRelationRepository extends IRepository<Tuple<Integer, Integer>, TestParticipantRelation> {

}