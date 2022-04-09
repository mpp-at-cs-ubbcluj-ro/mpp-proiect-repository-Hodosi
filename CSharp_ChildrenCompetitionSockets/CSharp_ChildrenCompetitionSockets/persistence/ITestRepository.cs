using System.Collections.Generic;
using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public interface ITestRepository : IRepository<int, Test>
    {
        IEnumerable<Test> findAllTestsForParticipant(int id);
    }
}