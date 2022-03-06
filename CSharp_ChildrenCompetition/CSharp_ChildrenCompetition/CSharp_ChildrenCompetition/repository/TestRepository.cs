using CSharp_ChildrenCompetition.model;

namespace CSharp_ChildrenCompetition.repository
{
    public class TestRepository : AbstractRepository<int, Test>
    {
        public TestRepository(Validator<Test> validator) : base(validator)
        {
            
        }
    }
}