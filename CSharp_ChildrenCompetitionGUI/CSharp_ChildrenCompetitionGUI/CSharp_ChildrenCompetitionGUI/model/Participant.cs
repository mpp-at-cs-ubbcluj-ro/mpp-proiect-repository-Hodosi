namespace CSharp_ChildrenCompetitionGUI.model
{
    public class Participant : Entity<int>
    {
        public string name { get; }
        public int age { get; }

        public Participant(string name, int age)
        {
            this.name = name;
            this.age = age;
        }
    }
}