using System;

namespace CSharp_ChildrenCompetition.repository
{
    public class ValidationException : SystemException
    {
        public ValidationException(string message) : base(message)
        {
            
        }
    }
}