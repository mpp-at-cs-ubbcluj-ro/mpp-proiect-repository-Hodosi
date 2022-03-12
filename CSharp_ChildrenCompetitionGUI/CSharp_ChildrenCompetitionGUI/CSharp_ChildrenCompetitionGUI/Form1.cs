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
using CSharp_ChildrenCompetitionGUI.model;
using CSharp_ChildrenCompetitionGUI.repository;
using log4net.Config;

namespace CSharp_ChildrenCompetitionGUI
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
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

        private void button1_Click(object sender, EventArgs e)
        {
            // throw new System.NotImplementedException();
            string resultText = "Start";
            string appendResultText ="Configuration settings for competionDB: " + GetConnectionStringByName("competitionDB") + "\n";
            resultText += appendResultText;
            
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("competitionDB"));

            appendResultText = "UserDB ...\n";
            resultText += appendResultText;

            IUserRepository<int, User> userRepository = new UserDbRepository(props);
            User user = new User("testFirstname", "testLastname", "testUsername", "testPassword");
            userRepository.save(user);
            
            label1.Text = resultText;
            button1.Enabled = false;
        }
    }
}