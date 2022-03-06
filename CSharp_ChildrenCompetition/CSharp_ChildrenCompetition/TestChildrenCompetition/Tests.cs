using System;
using CSharp_ChildrenCompetition.model;
using NUnit.Framework;

namespace TestChildrenCompetition
{
    [TestFixture]
    public class Tests
    {
        [Test]
        public void Test1()
        {
            Assert.True(true);
            int id = 1;
            string name = "A";
            int age = 10;
            Participant participant = new Participant(id, name, age);
            Assert.AreEqual(id, participant.id);
            Assert.AreEqual(name, participant.name);
            Assert.AreEqual(age, participant.age);
        }
    }
}