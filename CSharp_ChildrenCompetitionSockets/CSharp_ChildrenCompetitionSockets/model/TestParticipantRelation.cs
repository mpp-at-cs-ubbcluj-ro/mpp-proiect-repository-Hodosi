using System;

namespace CSharp_ChildrenCompetitionGUI.model
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