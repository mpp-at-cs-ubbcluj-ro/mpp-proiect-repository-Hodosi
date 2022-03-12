namespace CSharp_ChildrenCompetitionGUI.model
{
    public class Test : Entity<int>
    {
        public TestType type { get; }
        public TestAgeCategory category { get; }

        public Test(TestType type, TestAgeCategory category)
        {
            this.type = type;
            this.category = category;
        }
    }
}