using System.Collections.Generic;
using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public interface IRepository <ID, T > where T : HasId<ID>
    {
        int size();
        void save(T entity);
        void delete(ID id);
        void update(ID id, T entity);
        T findOne(ID id);
        IEnumerable<T> findAll();
    }
}