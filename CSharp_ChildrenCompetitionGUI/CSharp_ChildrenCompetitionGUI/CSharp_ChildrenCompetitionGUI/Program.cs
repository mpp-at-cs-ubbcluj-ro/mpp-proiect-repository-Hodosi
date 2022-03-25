using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using CSharp_ChildrenCompetitionGUI.model;
using CSharp_ChildrenCompetitionGUI.repository;
using CSharp_ChildrenCompetitionGUI.service;
using log4net.Config;

namespace CSharp_ChildrenCompetitionGUI
{

    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            log4net.Config.XmlConfigurator.Configure();
            XmlConfigurator.Configure(new System.IO.FileInfo(args[0]));
            
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("competitionDB"));

            Form controller = initController(props);
            
            // Application.Run(new Form1());
            Application.Run(controller);
        }
        
        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings =ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }

        static Form initController(IDictionary<String, string> props)
        {
            IUserRepository<int, User> userRepository = new UserDbRepository(props);
            IUserService<int, User> userService = new UserService(userRepository);

            ITestRepository<int, Test> testRepository = new TestDbRepository(props);
            IParticipantRepository<int, Participant>
                participantRepository = new ParticipantDbRepository(props);

            IParticipantService<int, Participant> participantService = new ParticipantService(participantRepository, testRepository);
            ITestService<int, Test> testService = new TestService(testRepository, participantRepository);

            ITestParticipantRelationRepository<Tuple<int, int>, TestParticipantRelation>
                testParticipantRelationRepository = new TestParticipantRelationDbRepository(props);
            ITestParticipantRelationService<Tuple<int, int>, TestParticipantRelation> testParticipantRelationService =
                new TestParticipantRelationService(testParticipantRelationRepository);

            Form1 controller = new Form1();
            
            controller.setUserService(userService);
            controller.setTestService(testService);
            controller.setParticipantService(participantService);
            controller.setTestParticipantRelationService(testParticipantRelationService);

            return controller;
        }
    }
}