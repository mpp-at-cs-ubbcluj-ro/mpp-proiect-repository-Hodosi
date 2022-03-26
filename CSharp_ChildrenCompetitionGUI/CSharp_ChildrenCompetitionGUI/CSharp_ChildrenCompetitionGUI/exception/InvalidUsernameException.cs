using System;

namespace CSharp_ChildrenCompetition.repository
{
    public class InvalidUsernameException : SystemException
    {
        public InvalidUsernameException() : base("invalid username")
        {
            
        }
    }
}