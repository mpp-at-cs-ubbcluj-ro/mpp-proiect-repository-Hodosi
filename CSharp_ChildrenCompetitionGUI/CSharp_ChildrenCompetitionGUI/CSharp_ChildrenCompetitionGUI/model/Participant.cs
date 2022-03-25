namespace CSharp_ChildrenCompetitionGUI.model
{
    public class Participant : Entity<int>
    {
        public string username { get; }
        public string name { get; }
        public int age { get; }

        public Participant(string username, string name, int age)
        {
            this.username = username;
            this.name = name;
            this.age = age;
        }
    }
}