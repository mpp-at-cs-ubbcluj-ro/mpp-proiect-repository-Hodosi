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
            panelMain.Visible = false;
            panelLogin.Visible = true;
        }
        private void initMainPage()
        {
            panelLogin.Visible = false;
            panelMain.Visible = true;
            updateViewTest();
            updateViewParticpants();
            updateComboBoxAge();
        }

        private void buttonLogout_Click(object sender, EventArgs e)
        {
            // throw new System.NotImplementedException();
            userService.logout();
            panelMain.Visible = false;
            panelLogin.Visible = true;
        }

        private void updateViewTest()
        {
            List<TestDTO> testDtos = testService.findAllTestDTOs();
            dataGridViewTests.Rows.Clear();
            foreach (TestDTO testDto in testDtos)
            {
                dataGridViewTests.Rows.Add(testDto.testType, testDto.testAgeCategory, testDto.noCompetitors);
            }

            dataGridViewTests.Rows[0].Selected = true;
        }

        private void updateViewParticpants()
        {
            // int testId = dataGridViewTests.SelectedRows[0].Index + 1;
            if (dataGridViewTests.CurrentCell == null)
            {
                return;
            }
            int testId = dataGridViewTests.CurrentCell.RowIndex + 1;
            List<Participant> participantList = participantService.findAllParticipantsForTest(testId);
            dataGridViewParticipants.Rows.Clear();
            foreach (Participant participant in participantList)
            {
                dataGridViewParticipants.Rows.Add(participant.username, participant.name, participant.age);
            }
            dataGridViewTests.Rows[testId - 1].Selected = true;
        }

        private void updateComboBoxAge()
        {
            var datasource = new List<int>();
            if (dataGridViewTests.SelectedRows.Count == 0)
            {
                return;
            }
            string ageCategory = dataGridViewTests.SelectedRows[0].Cells[1].Value.ToString();
            if(ageCategory.Contains("6"))
            {
                datasource.AddRange(Enumerable.Range(6, 3));
            }
            else if(ageCategory.Contains("9"))
            {
                datasource.AddRange(Enumerable.Range(9, 3));
            }
            else
            {
                datasource.AddRange(Enumerable.Range(12, 4));
            }

            comboBoxAgeSignUp.DataSource = datasource;
        }

        private void dataGridViewTests_SelectionChanged(object sender, EventArgs e)
        {
            // throw new System.NotImplementedException();
            updateViewParticpants();
            updateComboBoxAge();
        }

        private void buttonSignUpFoTest_Click(object sender, EventArgs e)
        {
            // throw new System.NotImplementedException();
            string username = textBoxUsernameSignUp.Text.Trim();
            string name = textBoxNameSignUp.Text.Trim();
            int age = int.Parse(comboBoxAgeSignUp.Text);

            if (username.Equals(""))
            {
                labelProgramError.Text = "Last error: Username can't be empty";
                return;
            }

            if (name.Equals(""))
            {
                labelProgramError.Text = "Last error: Name can't be empty";
                return;
            }
            int testId = dataGridViewTests.CurrentCell.RowIndex + 1;
            if (testId < 1 || testId > 9)
            {
                labelProgramError.Text = "Last error: No test selected";
                return;
            }

            try
            {
                participantService.save(username, name, age, testId);
            }
            catch (TestJoinedException exception)
            {
                labelProgramError.Text = "Last error: " + exception.Message;
                return;
            }
            catch (TestLimitException exception)
            {
                labelProgramError.Text = "Last error: " + exception.Message;
                return;
            }
            catch (InvalidUsernameException exception)
            {
                labelProgramError.Text = "Last error: " + exception.Message;
                return;
            }

            Participant participant = participantService.findByUsername(username);
            
            testParticipantRelationService.save(testId, participant.id);
            
            updateViewTest();
            updateViewParticpants();
            updateComboBoxAge();

        }
    }
}