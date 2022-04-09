using System;
using System.Collections.Generic;
using System.Data;
using CSharp_ChildrenCompetitionGUI.model;
// using log4net;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public class ParticipantDbRepository : IParticipantRepository
    {
        // private static readonly ILog log = LogManager.GetLogger("UserDbRepository");

        // private IDictionary<String, string> props;
        
        public ParticipantDbRepository(IDictionary<String, string> props)
        {
            // log.Info("Creating ParticipantDbRepository");
            // this.props = props;
        }
        
        public int size()
        {
            throw new System.NotImplementedException();
        }

        public void save(Participant entity)
        {
            // throw new System.NotImplementedException();
            var conn = DBUtils.getConnection();

            using (var command = conn.CreateCommand())
            {
                command.CommandText = "INSERT into participants(name, age, username) values (@nm,@ag,@usnm)";
                
                var paramNM = command.CreateParameter();
                paramNM.ParameterName = "@nm";
                paramNM.Value = entity.name;
                command.Parameters.Add(paramNM);

                var paramAG = command.CreateParameter();
                paramAG.ParameterName = "@ag";
                paramAG.Value = entity.age;
                command.Parameters.Add(paramAG);
                
                var paramUSNM = command.CreateParameter();
                paramUSNM.ParameterName = "@usnm";
                paramUSNM.Value = entity.username;
                command.Parameters.Add(paramUSNM);
                
                var result = command.ExecuteNonQuery();
            }
        }

        public void delete(int id)
        {
            throw new System.NotImplementedException();
        }

        public void update(int id, Participant entity)
        {
            throw new System.NotImplementedException();
        }

        public Participant findOne(int id)
        {
            // throw new System.NotImplementedException();
            // log.InfoFormat("Find one with value {0}", id);
            IDbConnection conn = DBUtils.getConnection();

            using (var comm = conn.CreateCommand())
            {
                comm.CommandText = "SELECT * from participants where id_participant = @id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idP = dataR.GetInt32(0);
                        String name = dataR.GetString(1);
                        int age= dataR.GetInt32(2);
                        string username = dataR.GetString(3);
                        Participant participant = new Participant(username, name, age);
                        participant.id = idP;
                        // log.InfoFormat("Exiting findOne with value{0}", participant);
                        return participant;
                    }
                }
            }
            // log.InfoFormat("exiting findOne with value{0}", null);
            return null;
        }

        public IEnumerable<Participant> findAll()
        {
            throw new System.NotImplementedException();
        }

        public Participant findByUsername(string username)
        {
            // throw new System.NotImplementedException();
            // log.InfoFormat("Find one with value {0}", username);
            IDbConnection conn = DBUtils.getConnection();

            using (var comm = conn.CreateCommand())
            {
                comm.CommandText = "SELECT * from participants where username = @usnm";
                
                IDbDataParameter paramUSNM = comm.CreateParameter();
                paramUSNM.ParameterName = "@usnm";
                paramUSNM.Value = username;
                comm.Parameters.Add(paramUSNM);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        String nameP = dataR.GetString(1);
                        int age = dataR.GetInt32(2);
                        string usernameP = dataR.GetString(3);
                        Participant participant = new Participant(usernameP, nameP, age);
                        participant.id = id;
                        // log.InfoFormat("Exiting findOne with value{0}", participant);
                        return participant;
                    }
                }
            }
            // log.InfoFormat("exiting findOne with value{0}", null);
            return null;
        }

        public IEnumerable<Participant> findAllParticipantsForTest(int id)
        {
            // throw new NotImplementedException();
            IDbConnection con = DBUtils.getConnection();
            IList<Participant> participantList = new List<Participant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "SELECT * FROM test_participant_relation TPR INNER JOIN participants P ON TPR.id_participant = P.id_participant WHERE id_test = @idt";
                // comm.CommandText = "SELECT P.id_participant, P.name, P.age, P.username  FROM test_participant_relation TPR INNER JOIN participants P ON TPR.id_participant = P.id_participant WHERE id_test = @idt";
                
                IDbDataParameter paramIDT = comm.CreateParameter();
                paramIDT.ParameterName = "@idt";
                paramIDT.Value = id;
                comm.Parameters.Add(paramIDT);
                
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        // int idP = dataR.GetInt32(0);
                        int idP = int.Parse(dataR["id_participant"].ToString());
                        // String nameP = dataR.GetString(1);
                        String nameP = dataR["name"].ToString();
                        // int age = dataR.GetInt32(2);
                        int age = int.Parse(dataR["age"].ToString());
                        // string usernameP = dataR.GetString(3);
                        string usernameP = dataR["username"].ToString();
                        Participant participant = new Participant(usernameP, nameP, age);
                        participant.id = idP;
                        participantList.Add(participant);
                    }
                }
            }

            return participantList;
        }
    }
}