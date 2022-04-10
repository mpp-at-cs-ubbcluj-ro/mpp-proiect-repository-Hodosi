using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using networking;
using services;

namespace client
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            ICompetitionServices server = new CompetitionServerProxy("127.0.0.1", 55556);
            CompetitionClientCtrl controller = new CompetitionClientCtrl(server);
            Application.Run(new Form1(controller));
        }
    }
}