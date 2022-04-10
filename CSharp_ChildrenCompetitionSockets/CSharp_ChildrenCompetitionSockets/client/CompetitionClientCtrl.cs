using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using CSharp_ChildrenCompetitionGUI.model;
using services;

namespace client
{
    public class CompetitionClientCtrl : ICompetitionObserver
    {
        public event EventHandler<CompetitionUserEventArgs> updateEvent; //ctrl calls it when it has received an update
        private readonly ICompetitionServices server;
        private User currentUser;
        public CompetitionClientCtrl(ICompetitionServices server)
        {
            this.server = server;
            currentUser = null;
        }

        public void login(string username, string password)
        {
            currentUser = new User("nullText", "nullText", username, password);
            server.login(currentUser, this);
            this.currentUser = server.findUserByUsername(username);
        }

        public void logout()
        {
            server.logout(currentUser, this);
            currentUser = null;
        }
        
        protected virtual void OnUserEvent(CompetitionUserEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }

        public void userLoggedIn(User user)
        {
            throw new NotImplementedException();
        }

        public void userLoggedOut(User user)
        {
            throw new NotImplementedException();
        }

        public void participantSaved()
        {
            // throw new NotImplementedException();
            CompetitionUserEventArgs userArgs =
                new CompetitionUserEventArgs(CompetitionUserEvent.SaveParticipant, null);
            OnUserEvent(userArgs);
        }

        // public void participantSavedCtrl(Participant participant)
        // {
        //     // throw new NotImplementedException();
        //     CompetitionUserEventArgs userArgs =
        //         new CompetitionUserEventArgs(CompetitionUserEvent.SaveParticipant, null);
        //     OnUserEvent(userArgs);
        //     server.saveParticipant(participant.username, participant.name, participant.age, participant.id);
        // }

        public List<TestDTO> findAllTestDTOs()
        {
            return server.findAllTestDTOs().ToList();
        }

        public List<Participant> findAllParticipantsForTest(int id)
        {
            return server.findAllParticipantsForTest(id).ToList();
        }

        public void saveParticipant(string username,string name,int age,int testId)
        {
            server.saveParticipant(username, name, age, testId);
        }

        public Participant findParticipantByUsername(string username)
        {
            return server.findParticipantByUsername(username);
        }

        public void saveRelation(int testId, int participantId)
        {
            server.saveRelation(testId, participantId);
        }
    }
}