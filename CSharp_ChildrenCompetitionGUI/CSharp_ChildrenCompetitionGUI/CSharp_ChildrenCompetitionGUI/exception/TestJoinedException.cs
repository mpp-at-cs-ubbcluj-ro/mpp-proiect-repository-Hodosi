using System;

namespace CSharp_ChildrenCompetition.repository
{
    public class TestJoinedException : SystemException
    {
        public TestJoinedException() : base("you already joined for this test")
        {
            
        }
    }
}