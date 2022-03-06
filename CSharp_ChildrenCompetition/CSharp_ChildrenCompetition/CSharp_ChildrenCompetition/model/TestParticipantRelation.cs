using System;
using CSharp_ChildrenCompetition.repository;

namespace CSharp_ChildrenCompetition.model
{
    public class TestParticipantRelation : HasId<Tuple<int, int>>
    {
        public Tuple<int, int> id { get; set; }

        public TestParticipantRelation(Tuple<int, int> id)
        {
            this.id = id;
        }
    }
}