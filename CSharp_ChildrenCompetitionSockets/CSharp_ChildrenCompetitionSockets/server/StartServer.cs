using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Threading;
using CSharp_ChildrenCompetitionGUI.repository;
using networking;
using services;

namespace server
{
    class StartServer
    {
        static void Main(string[] args)
        {
            IDictionary<String, string> props = new SortedList<String, String>();
            IUserRepository userRepository = new UserDbRepository(props);
            IParticipantRepository participantRepository = new ParticipantDbRepository(props);
            ITestRepository testRepository = new TestDbRepository(props);
            ITestParticipantRelationRepository testParticipantRelationRepository =
                new TestParticipantRelationDbRepository(props);

            ICompetitionServices services = new CompetitionServerImpl(userRepository, participantRepository,
                testRepository, testParticipantRelationRepository);

            SerialCompetitionServer server = new SerialCompetitionServer("127.0.0.1", 55556, services);
            server.Start();
            Console.WriteLine("Server started ...");
            Console.ReadLine();
        }
    }
    
    public class SerialCompetitionServer: ConcurrentServer 
    {
        private ICompetitionServices server;
        private CompetitionClientWorker worker;
        public SerialCompetitionServer(string host, int port, ICompetitionServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new CompetitionClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}