namespace CSharp_ChildrenCompetitionGUI.model
{
    public class User : Entity<int>
    {
        public string firstname { get; }
        public string lastname { get; }
        public string username { get; }
        public string password { get; }

        public User(string firstname, string lastname, string username, string password)
        {
            this.firstname = firstname;
            this.lastname = lastname;
            this.username = username;
            this.password = password;
        }
    }
}