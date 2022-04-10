using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using CSharp_ChildrenCompetitionGUI.model;
using CSharp_ChildrenCompetitionGUI.repository;
using networking;
using services;

namespace server
{
    public class CompetitionServerImpl : ICompetitionServices
    {
        private IUserRepository userRepository;
        private IParticipantRepository participantRepository;
        private ITestRepository testRepository;
        private ITestParticipantRelationRepository testParticipantRelationRepository;
        
        private readonly IDictionary <String, ICompetitionObserver> loggedClients;

        public CompetitionServerImpl(IUserRepository userRepository, IParticipantRepository participantRepository, ITestRepository testRepository, ITestParticipantRelationRepository testParticipantRelationRepository)
        {
            this.userRepository = userRepository;
            this.participantRepository = participantRepository;
            this.testRepository = testRepository;
            this.testParticipantRelationRepository = testParticipantRelationRepository;
            this.loggedClients = new Dictionary<string, ICompetitionObserver>();
        }

        public void login(User user, ICompetitionObserver client)
        {
            Console.WriteLine("SERVER IMPLEMENTATION - LOGIN");
            // throw new System.NotImplementedException();
            User crtUser = userRepository.findByUsername(user.username);
            if (crtUser != null)
            {
                if (loggedClients.ContainsKey(user.username))
                {
                    throw new CompetitionException("User already logged in");
                }
                loggedClients[user.username] = client;
                    // notifyFriendsLoggedIn(user);
            }
            else
            {
                throw new CompetitionException("Authentication failed.");
            }
        }
        
        // private void notifyFriendsLoggedIn(User user){
        //     IEnumerable<User> friends=userRepository.getFriendsOf(user);
        //     Console.WriteLine("notify logged friends "+friends.Count());
        //     foreach(User us in friends){
        //         if (loggedClients.ContainsKey(us.Id))
        //         {
        //             IChatObserver chatClient = loggedClients[us.Id];
        //             Task.Run(() => chatClient.friendLoggedIn(user));
        //         }
        //     }
        // }
        //
        // private void notifyFriendsLoggedOut(User user) {
        //     IEnumerable<User> friends=userRepository.getFriendsOf(user);
        //     foreach(User us in friends){
        //         if (loggedClients.ContainsKey(us.Id))
        //         {
        //             IChatObserver chatClient = loggedClients[us.Id];
        //             Task.Run(() =>chatClient.friendLoggedOut(user));
        //         }
        //     }
        // }
        
        private void notifySavedParticipant() {
            foreach(KeyValuePair<string, ICompetitionObserver> entry in loggedClients)
            {
                Task.Run(() => entry.Value.participantSaved());
            }
        }

        public void logout(User user, ICompetitionObserver client)
        {
            // throw new System.NotImplementedException();
            ICompetitionObserver localClient = loggedClients[user.username];
            if (localClient == null)
                throw new CompetitionException("User "  + user.username + " is not logged");
            loggedClients.Remove(user.username);
            // notifyFriendsLoggedOut();
        }

        public User findUserByUsername(string username)
        {
            // throw new System.NotImplementedException();
            User user = userRepository.findByUsername(username);
            return user;
        }

        public Participant findParticipantByUsername(string username)
        {
            // throw new System.NotImplementedException();
            Participant participant = participantRepository.findByUsername(username);
            return participant;
        }

        public void saveParticipant(string username, string name, int age, int testId)
        {
            // throw new System.NotImplementedException();
            Participant participant = participantRepository.findByUsername(username);
            if (participant == null)
            {
                Participant newParticipant = new Participant(username, name, age);
                participantRepository.save(newParticipant);
                return;
            }

            if (participant.age != age || !participant.name.Equals(name))
            {
                throw new CompetitionException("Invalid username");
            }
            
            List<Test> testList = (List<Test>) testRepository.findAllTestsForParticipant(participant.id);
            if(testList.Count >= 2){
                throw new CompetitionException("Test limit exception");
            }

            if(testList[0].id == testId){
                throw new CompetitionException("Test joined exception");
            }
        }

        public void saveRelation(int idTest, int idParticipant)
        {
            // throw new System.NotImplementedException();
            Tuple<int, int> id = new Tuple<int, int>(idTest, idParticipant);
            TestParticipantRelation testParticipantRelation = new TestParticipantRelation(id);
            this.testParticipantRelationRepository.save(testParticipantRelation);
            notifySavedParticipant();
        }

        public User[] getLoggedUsers(User user)
        {
            // throw new System.NotImplementedException();
            IEnumerable<User> users = userRepository.findAll();
            HashSet<User> result = new HashSet<User>();
            foreach(User us in users){
                if (loggedClients.ContainsKey(us.username))
                {
                    result.Add(new User(us.firstname, us.lastname, us.username, us.password));
                }
            }

            return result.ToArray();
        }

        public TestDTO[] findAllTestDTOs()
        {
            // throw new System.NotImplementedException();
            IEnumerable<Test> tests = testRepository.findAll();
            IList<TestDTO> result = new List<TestDTO>();

            foreach (Test test in tests)
            {
                string type = test.type.type;
                TestAgeCategory testAgeCategory = test.category;
                String ageCategory = testAgeCategory.minAge.ToString() + " - " + testAgeCategory.maxAge.ToString();

                IEnumerable<Participant> participantsForTest =
                    participantRepository.findAllParticipantsForTest(test.id);

                int noCompetitors = ((List<Participant>) participantsForTest).Count;

                TestDTO testDto = new TestDTO(type, ageCategory, noCompetitors);
                testDto.id = test.id;
                result.Add(testDto);
            }
            
            return result.ToArray();
        }

        public Participant[] findAllParticipantsForTest(int id)
        {
            // throw new System.NotImplementedException();
            List<Participant> participants = (List<Participant>) participantRepository.findAllParticipantsForTest(id);
            return participants.ToArray();
        }
    }
}