using CSharp_ChildrenCompetition.repository;

namespace CSharp_ChildrenCompetition.model
{
    public class Test : HasId<int>
    {
        public int id { get; set; }
        private TestType type { get; }
        private TestAgeCategory category { get; }

        public Test(TestType type, TestAgeCategory category, int id)
        {
            this.type = type;
            this.category = category;
            this.id = id;
        }

        public override string ToString()
        {
            return "Type: " + this.type + " Category: " + this.category;
        }
    }
}