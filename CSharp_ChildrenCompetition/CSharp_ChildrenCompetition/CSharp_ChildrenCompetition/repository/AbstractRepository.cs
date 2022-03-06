using System.Collections.Generic;

namespace CSharp_ChildrenCompetition.repository
{
    public class AbstractRepository<ID, T> : IRepository<ID, T> where T : HasId<ID>
    {
        protected Dictionary<ID, T> entities;
        protected Validator<T> validator;

        public AbstractRepository(Validator<T> validator)
        {
            this.entities = new Dictionary<ID, T>();
            this.validator = validator;
        }

        public int size()
        {
            return 0;
        }

        public void save(T entity)
        {
            
        }

        public void delete(ID id)
        {
            
        }

        public void update(ID id, T entity)
        {
            
        }

        public T findOne(ID id)
        {
            return default;
        }

        public IEnumerable<T> findAll()
        {
            return default;
        }

    }
}