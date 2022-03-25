using System.Collections.Generic;
using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.service
{
    public interface ITestService<ID, T> where T : HasId<ID>
    {
        List<TestDTO> findAllTestDTOs();
    }
}