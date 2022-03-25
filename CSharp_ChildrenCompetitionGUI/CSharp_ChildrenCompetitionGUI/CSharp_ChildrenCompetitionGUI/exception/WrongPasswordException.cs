using System;

namespace CSharp_ChildrenCompetition.repository
{
    public class WrongPasswordException : SystemException
    {
        public WrongPasswordException() : base("invalid password")
        {
            
        }
    }
}