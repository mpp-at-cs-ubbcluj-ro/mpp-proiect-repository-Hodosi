namespace CSharp_ChildrenCompetitionGUI.model
{
    public class TestDTO : Entity<int>
    {
        public string testType { get; }
        public string testAgeCategory { get; }
        public int noCompetitors { get; }

        public TestDTO(string testType, string testAgeCategory, int noCompetitors)
        {
            this.testType = testType;
            this.testAgeCategory = testAgeCategory;
            this.noCompetitors = noCompetitors;
        }
    }
}