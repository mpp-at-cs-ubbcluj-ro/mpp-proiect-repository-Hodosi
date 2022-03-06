// using System;
using CSharp_ChildrenCompetition.repository;

namespace CSharp_ChildrenCompetition.model
{
    public class Participant : HasId<int>
    {
        public int id { get; set; }
        public string name { get; }
        public int age { get; }

        public Participant(int participantId, string name, int age)
        {
            this.id = participantId;
            this.name = name;
            this.age = age;
        }

        public override string ToString()
        {
            return "Name: " + this.name + " age: " + this.age;
        }
    }
}