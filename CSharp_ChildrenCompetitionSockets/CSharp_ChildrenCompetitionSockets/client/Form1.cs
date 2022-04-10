using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using CSharp_ChildrenCompetitionGUI.model;

namespace client
{
    public partial class Form1 : Form
    {

        private CompetitionClientCtrl controller;
        
        public Form1(CompetitionClientCtrl controller)
        {
            InitializeComponent();
            this.controller = controller;
            initLoginPage();
        }
        

        private void loginButton_Click(object sender, EventArgs e)
        {
            // throw new System.NotImplementedException();
            string username = textBoxUsernameLogin.Text.Trim();
            string password = textBoxPasswordLogin.Text.Trim();
            
            try
            {
                controller.login(username, password);
                controller.updateEvent += userUpdate;
                initMainPage();
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Login Error " + ex.Message/*+ex.StackTrace*/, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
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
            try
            {
                controller.logout();
                controller.updateEvent -= userUpdate;
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Logout" + ex.Message/*+ex.StackTrace*/, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            panelMain.Visible = false;
            panelLogin.Visible = true;
        }

        private void updateViewTest()
        {
            try
            {
                List<TestDTO> testDtos = controller.findAllTestDTOs();
                dataGridViewTests.Rows.Clear();
                foreach (TestDTO testDto in testDtos)
                {
                    dataGridViewTests.Rows.Add(testDto.testType, testDto.testAgeCategory, testDto.noCompetitors);
                }
        
                dataGridViewTests.Rows[0].Selected = true;
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Update View Tests " + ex.Message/*+ex.StackTrace*/, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

        }

        private void updateViewParticpants()
        {
            // int testId = dataGridViewTests.SelectedRows[0].Index + 1;
            if (dataGridViewTests.CurrentCell == null)
            {
                return;
            }
            int testId = dataGridViewTests.CurrentCell.RowIndex + 1;
            
            List<Participant> participantList = controller.findAllParticipantsForTest(testId);
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
                controller.saveParticipant(username, name, age, testId);
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Save participant" + ex.Message/*+ex.StackTrace*/, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

            Participant participant = null;
            
            try
            {
                participant = controller.findParticipantByUsername(username);
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Find participantby username" + ex.Message/*+ex.StackTrace*/, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            
            try
            {
                controller.saveRelation(testId, participant.id);
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Save Relation" + ex.Message/*+ex.StackTrace*/, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

            
            updateViewTest();
            updateViewParticpants();
            updateComboBoxAge();
        }

        public void userUpdate(object sender, CompetitionUserEventArgs e)
        {
            if (e.UserEventType == CompetitionUserEvent.SaveParticipant)
            {
                BeginInvoke(new updateAllCallback(updateAll));
            }
        }

        public void updateAll()
        {
            updateViewTest();
            updateViewParticpants();
            updateComboBoxAge();
        }

        public delegate void updateAllCallback();
    }
}