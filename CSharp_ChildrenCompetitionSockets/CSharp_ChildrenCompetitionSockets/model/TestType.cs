using System;

namespace CSharp_ChildrenCompetitionGUI.model
{
    [Serializable]
    public class TestType : Entity<int>
    {
        //DRAWING, TREASURE_HUNT, POETRY
        public string type { get; }

        public TestType(string type)
        {
            this.type = type;
        }
    }
}