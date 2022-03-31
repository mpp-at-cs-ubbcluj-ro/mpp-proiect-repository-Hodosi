package competition.persistence;

import model.Participant;

//public interface IParticipantRepository<ID, T extends HasId<ID>> extends IRepository<ID, T>{
//    T findByUsername(String username);
//    Iterable<T> findAllParticipantsForTest(int id);
//}

public interface IParticipantRepository extends repository.IRepository<Integer, Participant> {
    Participant findByUsername(String username);
    Iterable<Participant> findAllParticipantsForTest(int id);
}
