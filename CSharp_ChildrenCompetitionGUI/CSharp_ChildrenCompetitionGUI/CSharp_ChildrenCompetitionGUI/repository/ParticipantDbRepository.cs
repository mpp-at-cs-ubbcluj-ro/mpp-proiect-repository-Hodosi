using System;
using System.Collections.Generic;
using System.Data;
using CSharp_ChildrenCompetitionGUI.model;
using log4net;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public class ParticipantDbRepository : IParticipantRepository<int, Participant>
    {
        private static readonly ILog log = LogManager.GetLogger("UserDbRepository");

        private IDictionary<String, string> props;
        
        public ParticipantDbRepository(IDictionary<String, string> props)
        {
            log.Info("Creating ParticipantDbRepository");
            this.props = props;
        }
        
        public int size()
        {
            throw new System.NotImplementedException();
        }

        public void save(Participant entity)
        {
            // throw new System.NotImplementedException();
            var conn = DBUtils.getConnection(props);

            using (var command = conn.CreateCommand())
            {
                command.CommandText = "INSERT into participants(name, age) values (@nm,@ag)";
                var paramNM = command.CreateParameter();
                paramNM.ParameterName = "@nm";
                paramNM.Value = entity.name;
                command.Parameters.Add(paramNM);

                var paramAG = command.CreateParameter();
                paramAG.ParameterName = "@ag";
                paramAG.Value = entity.age;
                command.Parameters.Add(paramAG);
                
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
            log.InfoFormat("Find one with value {0}", id);
            IDbConnection conn = DBUtils.getConnection(props);

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
                        Participant participant = new Participant(name, age);
                        participant.id = idP;
                        log.InfoFormat("Exiting findOne with value{0}", participant);
                        return participant;
                    }
                }
            }
            log.InfoFormat("exiting findOne with value{0}", null);
            return null;
        }

        public IEnumerable<Participant> findAll()
        {
            throw new System.NotImplementedException();
        }

        public Participant findByName(string name)
        {
            // throw new System.NotImplementedException();
            log.InfoFormat("Find one with value {0}", name);
            IDbConnection conn = DBUtils.getConnection(props);

            using (var comm = conn.CreateCommand())
            {
                comm.CommandText = "SELECT * from participants where name = @nm";
                IDbDataParameter paramNM = comm.CreateParameter();
                paramNM.ParameterName = "@nm";
                paramNM.Value = name;
                comm.Parameters.Add(paramNM);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        String nameP = dataR.GetString(1);
                        int age = dataR.GetInt32(2);
                        Participant participant = new Participant(nameP, age);
                        participant.id = id;
                        log.InfoFormat("Exiting findOne with value{0}", participant);
                        return participant;
                    }
                }
            }
            log.InfoFormat("exiting findOne with value{0}", null);
            return null;
        }
    }
}