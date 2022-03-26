namespace CSharp_ChildrenCompetitionGUI
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }

            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.errorLoginLabel = new System.Windows.Forms.Label();
            this.loginButton = new System.Windows.Forms.Button();
            this.textBoxPasswordLogin = new System.Windows.Forms.TextBox();
            this.textBoxUsernameLogin = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.panelLogin = new System.Windows.Forms.Panel();
            this.panelMain = new System.Windows.Forms.Panel();
            this.buttonSignUpFoTest = new System.Windows.Forms.Button();
            this.labelProgramError = new System.Windows.Forms.Label();
            this.comboBoxAgeSignUp = new System.Windows.Forms.ComboBox();
            this.textBoxNameSignUp = new System.Windows.Forms.TextBox();
            this.textBoxUsernameSignUp = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.buttonLogout = new System.Windows.Forms.Button();
            this.dataGridViewParticipants = new System.Windows.Forms.DataGridView();
            this.ColumnUsername = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.ColumnName = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.ColumnAge = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTests = new System.Windows.Forms.DataGridView();
            this.ColumType = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.ColumnAgeCategory = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.ColumnCompetitors = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.label3 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            this.panelLogin.SuspendLayout();
            this.panelMain.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewParticipants)).BeginInit();
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewTests)).BeginInit();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.errorLoginLabel);
            this.groupBox1.Controls.Add(this.loginButton);
            this.groupBox1.Controls.Add(this.textBoxPasswordLogin);
            this.groupBox1.Controls.Add(this.textBoxUsernameLogin);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Location = new System.Drawing.Point(13, 14);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(356, 209);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Login";
            // 
            // errorLoginLabel
            // 
            this.errorLoginLabel.ForeColor = System.Drawing.Color.Red;
            this.errorLoginLabel.Location = new System.Drawing.Point(39, 172);
            this.errorLoginLabel.Name = "errorLoginLabel";
            this.errorLoginLabel.Size = new System.Drawing.Size(292, 27);
            this.errorLoginLabel.TabIndex = 5;
            this.errorLoginLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // loginButton
            // 
            this.loginButton.Location = new System.Drawing.Point(117, 144);
            this.loginButton.Name = "loginButton";
            this.loginButton.Size = new System.Drawing.Size(97, 25);
            this.loginButton.TabIndex = 4;
            this.loginButton.Text = "Login";
            this.loginButton.UseVisualStyleBackColor = true;
            this.loginButton.Click += new System.EventHandler(this.loginButton_Click);
            // 
            // textBoxPasswordLogin
            // 
            this.textBoxPasswordLogin.Location = new System.Drawing.Point(141, 100);
            this.textBoxPasswordLogin.Name = "textBoxPasswordLogin";
            this.textBoxPasswordLogin.PasswordChar = '*';
            this.textBoxPasswordLogin.Size = new System.Drawing.Size(191, 22);
            this.textBoxPasswordLogin.TabIndex = 3;
            this.textBoxPasswordLogin.Text = "testPassword";
            // 
            // textBoxUsernameLogin
            // 
            this.textBoxUsernameLogin.Location = new System.Drawing.Point(141, 59);
            this.textBoxUsernameLogin.Name = "textBoxUsernameLogin";
            this.textBoxUsernameLogin.Size = new System.Drawing.Size(191, 22);
            this.textBoxUsernameLogin.TabIndex = 2;
            this.textBoxUsernameLogin.Text = "testUsername";
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(39, 94);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(96, 35);
            this.label2.TabIndex = 1;
            this.label2.Text = "Password:";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(39, 53);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(96, 35);
            this.label1.TabIndex = 0;
            this.label1.Text = "Username:";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // panelLogin
            // 
            this.panelLogin.Controls.Add(this.groupBox1);
            this.panelLogin.Location = new System.Drawing.Point(319, 173);
            this.panelLogin.Name = "panelLogin";
            this.panelLogin.Size = new System.Drawing.Size(381, 237);
            this.panelLogin.TabIndex = 1;
            // 
            // panelMain
            // 
            this.panelMain.Controls.Add(this.panelLogin);
            this.panelMain.Controls.Add(this.buttonSignUpFoTest);
            this.panelMain.Controls.Add(this.labelProgramError);
            this.panelMain.Controls.Add(this.comboBoxAgeSignUp);
            this.panelMain.Controls.Add(this.textBoxNameSignUp);
            this.panelMain.Controls.Add(this.textBoxUsernameSignUp);
            this.panelMain.Controls.Add(this.label9);
            this.panelMain.Controls.Add(this.label8);
            this.panelMain.Controls.Add(this.label7);
            this.panelMain.Controls.Add(this.label6);
            this.panelMain.Controls.Add(this.label5);
            this.panelMain.Controls.Add(this.label4);
            this.panelMain.Controls.Add(this.buttonLogout);
            this.panelMain.Controls.Add(this.dataGridViewParticipants);
            this.panelMain.Controls.Add(this.dataGridViewTests);
            this.panelMain.Controls.Add(this.label3);
            this.panelMain.Location = new System.Drawing.Point(12, 12);
            this.panelMain.Name = "panelMain";
            this.panelMain.Size = new System.Drawing.Size(1158, 629);
            this.panelMain.TabIndex = 2;
            // 
            // buttonSignUpFoTest
            // 
            this.buttonSignUpFoTest.Location = new System.Drawing.Point(233, 584);
            this.buttonSignUpFoTest.Name = "buttonSignUpFoTest";
            this.buttonSignUpFoTest.Size = new System.Drawing.Size(108, 38);
            this.buttonSignUpFoTest.TabIndex = 14;
            this.buttonSignUpFoTest.Text = "Sign up";
            this.buttonSignUpFoTest.UseVisualStyleBackColor = true;
            this.buttonSignUpFoTest.Click += new System.EventHandler(this.buttonSignUpFoTest_Click);
            // 
            // labelProgramError
            // 
            this.labelProgramError.ForeColor = System.Drawing.Color.Red;
            this.labelProgramError.Location = new System.Drawing.Point(99, 562);
            this.labelProgramError.Name = "labelProgramError";
            this.labelProgramError.Size = new System.Drawing.Size(404, 19);
            this.labelProgramError.TabIndex = 13;
            this.labelProgramError.Text = "Last error: null";
            this.labelProgramError.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // comboBoxAgeSignUp
            // 
            this.comboBoxAgeSignUp.FormattingEnabled = true;
            this.comboBoxAgeSignUp.Location = new System.Drawing.Point(287, 525);
            this.comboBoxAgeSignUp.Name = "comboBoxAgeSignUp";
            this.comboBoxAgeSignUp.Size = new System.Drawing.Size(216, 24);
            this.comboBoxAgeSignUp.TabIndex = 12;
            // 
            // textBoxNameSignUp
            // 
            this.textBoxNameSignUp.Location = new System.Drawing.Point(287, 491);
            this.textBoxNameSignUp.Name = "textBoxNameSignUp";
            this.textBoxNameSignUp.Size = new System.Drawing.Size(216, 22);
            this.textBoxNameSignUp.TabIndex = 11;
            // 
            // textBoxUsernameSignUp
            // 
            this.textBoxUsernameSignUp.Location = new System.Drawing.Point(287, 454);
            this.textBoxUsernameSignUp.Name = "textBoxUsernameSignUp";
            this.textBoxUsernameSignUp.Size = new System.Drawing.Size(216, 22);
            this.textBoxUsernameSignUp.TabIndex = 10;
            // 
            // label9
            // 
            this.label9.Location = new System.Drawing.Point(99, 522);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(182, 27);
            this.label9.TabIndex = 9;
            this.label9.Text = "Age:";
            this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label8
            // 
            this.label8.Location = new System.Drawing.Point(99, 486);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(182, 27);
            this.label8.TabIndex = 8;
            this.label8.Text = "Name:";
            this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label7
            // 
            this.label7.Location = new System.Drawing.Point(99, 449);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(182, 27);
            this.label7.TabIndex = 7;
            this.label7.Text = "Username:";
            this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label6
            // 
            this.label6.Location = new System.Drawing.Point(99, 413);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(404, 21);
            this.label6.TabIndex = 6;
            this.label6.Text = " * Please select a test before sign up";
            // 
            // label5
            // 
            this.label5.Location = new System.Drawing.Point(99, 377);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(404, 23);
            this.label5.TabIndex = 5;
            this.label5.Text = "Sign up for a test";
            this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label4
            // 
            this.label4.Location = new System.Drawing.Point(602, 21);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(542, 20);
            this.label4.TabIndex = 4;
            this.label4.Text = "Participants";
            this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // buttonLogout
            // 
            this.buttonLogout.Location = new System.Drawing.Point(840, 588);
            this.buttonLogout.Name = "buttonLogout";
            this.buttonLogout.Size = new System.Drawing.Size(108, 38);
            this.buttonLogout.TabIndex = 3;
            this.buttonLogout.Text = "Logout";
            this.buttonLogout.UseVisualStyleBackColor = true;
            this.buttonLogout.Click += new System.EventHandler(this.buttonLogout_Click);
            // 
            // dataGridViewParticipants
            // 
            this.dataGridViewParticipants.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewParticipants.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {this.ColumnUsername, this.ColumnName, this.ColumnAge});
            this.dataGridViewParticipants.Location = new System.Drawing.Point(602, 51);
            this.dataGridViewParticipants.Name = "dataGridViewParticipants";
            this.dataGridViewParticipants.RowTemplate.Height = 24;
            this.dataGridViewParticipants.Size = new System.Drawing.Size(542, 531);
            this.dataGridViewParticipants.TabIndex = 2;
            // 
            // ColumnUsername
            // 
            this.ColumnUsername.HeaderText = "Username";
            this.ColumnUsername.Name = "ColumnUsername";
            this.ColumnUsername.Width = 120;
            // 
            // ColumnName
            // 
            this.ColumnName.HeaderText = "Name";
            this.ColumnName.Name = "ColumnName";
            this.ColumnName.Width = 120;
            // 
            // ColumnAge
            // 
            this.ColumnAge.HeaderText = "Age";
            this.ColumnAge.Name = "ColumnAge";
            this.ColumnAge.Width = 120;
            // 
            // dataGridViewTests
            // 
            this.dataGridViewTests.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewTests.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {this.ColumType, this.ColumnAgeCategory, this.ColumnCompetitors});
            this.dataGridViewTests.Location = new System.Drawing.Point(22, 51);
            this.dataGridViewTests.Name = "dataGridViewTests";
            this.dataGridViewTests.RowTemplate.Height = 24;
            this.dataGridViewTests.Size = new System.Drawing.Size(540, 313);
            this.dataGridViewTests.TabIndex = 1;
            this.dataGridViewTests.SelectionChanged += new System.EventHandler(this.dataGridViewTests_SelectionChanged);
            // 
            // ColumType
            // 
            this.ColumType.HeaderText = "Type";
            this.ColumType.Name = "ColumType";
            this.ColumType.Width = 120;
            // 
            // ColumnAgeCategory
            // 
            this.ColumnAgeCategory.HeaderText = "Age category";
            this.ColumnAgeCategory.Name = "ColumnAgeCategory";
            this.ColumnAgeCategory.Width = 120;
            // 
            // ColumnCompetitors
            // 
            this.ColumnCompetitors.HeaderText = "Competitors";
            this.ColumnCompetitors.Name = "ColumnCompetitors";
            this.ColumnCompetitors.Width = 120;
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(22, 14);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(540, 27);
            this.label3.TabIndex = 0;
            this.label3.Text = "Tests";
            this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1182, 653);
            this.Controls.Add(this.panelMain);
            this.Name = "Form1";
            this.Text = "Competition";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.panelLogin.ResumeLayout(false);
            this.panelMain.ResumeLayout(false);
            this.panelMain.PerformLayout();
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewParticipants)).EndInit();
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewTests)).EndInit();
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.Button buttonSignUpFoTest;

        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox textBoxUsernameSignUp;
        private System.Windows.Forms.TextBox textBoxNameSignUp;
        private System.Windows.Forms.ComboBox comboBoxAgeSignUp;
        private System.Windows.Forms.Label labelProgramError;

        private System.Windows.Forms.Label label7;

        private System.Windows.Forms.Label label6;

        private System.Windows.Forms.Label label5;

        private System.Windows.Forms.Label label4;

        private System.Windows.Forms.DataGridViewTextBoxColumn ColumnUsername;
        private System.Windows.Forms.DataGridViewTextBoxColumn ColumnName;
        private System.Windows.Forms.DataGridViewTextBoxColumn ColumnAge;
        private System.Windows.Forms.Button buttonLogout;

        private System.Windows.Forms.DataGridViewTextBoxColumn ColumType;
        private System.Windows.Forms.DataGridViewTextBoxColumn ColumnAgeCategory;
        private System.Windows.Forms.DataGridViewTextBoxColumn ColumnCompetitors;
        private System.Windows.Forms.DataGridView dataGridViewParticipants;

        private System.Windows.Forms.DataGridView dataGridViewTests;

        private System.Windows.Forms.Label label3;

        private System.Windows.Forms.Panel panelMain;

        private System.Windows.Forms.Panel panelLogin;

        private System.Windows.Forms.Label errorLoginLabel;

        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBoxUsernameLogin;
        private System.Windows.Forms.TextBox textBoxPasswordLogin;
        private System.Windows.Forms.Button loginButton;

        private System.Windows.Forms.Label label1;

        private System.Windows.Forms.GroupBox groupBox1;

        #endregion
    }
}