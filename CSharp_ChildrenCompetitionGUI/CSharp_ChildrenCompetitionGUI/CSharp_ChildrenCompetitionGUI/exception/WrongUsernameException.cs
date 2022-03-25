using System;

namespace CSharp_ChildrenCompetition.repository
{
    public class WrongUsernameException : SystemException
    {
        public WrongUsernameException() : base("invalid username")
        {
            
        }
    }
}