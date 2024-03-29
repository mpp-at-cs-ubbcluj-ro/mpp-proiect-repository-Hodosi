﻿using System;

namespace CSharp_ChildrenCompetitionGUI.model
{
    [Serializable]
    public class TestAgeCategory : Entity<int>
    {
        public int minAge { get; }
        public int maxAge { get; }

        public TestAgeCategory(int minAge, int maxAge)
        {
            this.minAge = minAge;
            this.maxAge = maxAge;
        }
    }
}