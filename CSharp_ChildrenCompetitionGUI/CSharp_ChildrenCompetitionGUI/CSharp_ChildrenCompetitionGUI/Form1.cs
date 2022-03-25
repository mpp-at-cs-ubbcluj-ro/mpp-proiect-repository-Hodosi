using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.ComponentModel.Com2Interop;
using CSharp_ChildrenCompetition.repository;
using CSharp_ChildrenCompetitionGUI.model;
using CSharp_ChildrenCompetitionGUI.repository;
using CSharp_ChildrenCompetitionGUI.service;
using log4net.Config;

namespace CSharp_ChildrenCompetitionGUI
{
    public partial class Form1 : Form
    {
        private IUserService<int, User> userService;
        private ITestService<int, Test> testService;
        private IParticipantService<int, Participant> participantService;
        private ITestParticipantRelationService<Tuple<int, int>, TestParticipantRelation> testParticipantRelationService;

        public Form1()
        {
            InitializeComponent();
            initLoginPage();
        }

        public void setUserService(IUserService<int, User> userService)
        {
            this.userService = userService;
        }

        public void setTestService(ITestService<int, Test> testService)
        {
            this.testService = testService;
        }

        public void setParticipantService(IParticipantService<int, Participant> participantService)
        {
            this.participantService = participantService;
        }

        public void setTestParticipantRelationService(ITestParticipantRelationService<Tuple<int, int>, TestParticipantRelation> testParticipantRelationService)
        {
            this.testParticipantRelationService = testParticipantRelationService;
        }
        
        // static string GetConnectionStringByName(string name)
        // {
        //     // Assume failure.
        //     string returnValue = null;
        //
        //     // Look for the name in the connectionStrings section.
        //     ConnectionStringSettings settings =ConfigurationManager.ConnectionStrings[name];
        //
        //     // If found, return the connection string.
        //     if (settings != null)
        //         returnValue = settings.ConnectionString;
        //
        //     return returnValue;
        // }
        //
        // private void initStructure()
        // {
        //     IDictionary<String, string> props = new SortedList<String, String>();
        //     props.Add("ConnectionString", GetConnectionStringByName("competitionDB"));
        //     
        //     
        // }

        // private void button1_Click(object sender, EventArgs e)
        // {
        //     // throw new System.NotImplementedException();
        //     string resultText = "Start";
        //     string appendResultText ="Configuration settings for competionDB: " + GetConnectionStringByName("competitionDB") + "\n";
        //     resultText += appendResultText;
        //     
        //     IDictionary<String, string> props = new SortedList<String, String>();
        //     props.Add("ConnectionString", GetConnectionStringByName("competitionDB"));
        //
        //     appendResultText = "UserDB ...\n";
        //     resultText += appendResultText;
        //
        //     IUserRepository<int, User> userRepository = new UserDbRepository(props);
        //     User user = new User("testFirstname", "testLastname", "testUsername", "testPassword");
        //     // userRepository.save(user);
        //
        //     User userById = userRepository.findOne(1);
        //     appendResultText = userById.username + "\n";
        //     resultText += appendResultText;
        //     
        //     User userByUsername = userRepository.findByUsername("testUsername");
        //     appendResultText = userByUsername.username + "\n";
        //     resultText += appendResultText;
        //
        //     IParticipantRepository<int, Participant> participantRepository = new ParticipantDbRepository(props);
        //     Participant participant = new Participant("testName", 6);
        //     // participantRepository.save(participant);
        //
        //     Participant participantById = participantRepository.findOne(1);
        //     appendResultText = participantById.name + "\n";
        //     resultText += appendResultText;
        //
        //     Participant participantByName = participantRepository.findByName("testName");
        //     appendResultText = participantByName.name + "\n";
        //     resultText += appendResultText;
        //     
        //     
        //         
        //     label1.Text = resultText;
        //     button1.Enabled = false;
        // }


        private void loginButton_Click(object sender, EventArgs e)
        {
            // throw new System.NotImplementedException();
            string username = textBoxUsernameLogin.Text.Trim();
            string password = textBoxPasswordLogin.Text.Trim();

            try
            {
                userService.login(username, password);
                initMainPage();
            }
            catch (WrongUsernameException exception)
            {
                errorLoginLabel.Text = exception.Message;
            }
            catch (WrongPasswordException exception)
            {
                errorLoginLabel.Text = exception.Message;
            }
        }

        private void initLoginPage()
        {
            // panelMain.Visible = false;
            panelLogin.Visible = true;
        }
        private void initMainPage()
        {
            panelLogin.Visible = false;
            panelMain.Visible = true;
        }

        private void buttonLogout_Click(object sender, EventArgs e)
        {
            // throw new System.NotImplementedException();
            userService.logout();
            panelLogin.Visible = true;
            panelMain.Visible = false;
        }
    }
}