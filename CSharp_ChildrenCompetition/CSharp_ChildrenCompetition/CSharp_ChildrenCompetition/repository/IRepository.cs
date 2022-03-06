using System.Collections;
using System.Collections.Generic;

namespace CSharp_ChildrenCompetition.repository
{
    public interface IRepository<ID, T>
    {
        int size();
        void save(T entity);
        void delete(ID id);
        void update(ID id, T entity);
        T findOne(ID id);
        IEnumerable<T> findAll();
    }
}