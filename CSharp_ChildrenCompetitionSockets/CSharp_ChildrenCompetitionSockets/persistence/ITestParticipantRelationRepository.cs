using System;
using System.Collections.Generic;
using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public interface ITestParticipantRelationRepository : IRepository<Tuple<int, int>, TestParticipantRelation>
    {

    }
}