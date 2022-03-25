using System;

namespace CSharp_ChildrenCompetition.repository
{
    public class TestLimitException : SystemException
    {
        public TestLimitException() : base("you already joined for two test")
        {
            
        }
    }
}