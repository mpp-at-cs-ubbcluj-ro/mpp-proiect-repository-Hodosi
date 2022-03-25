using System;
using System.Collections.Generic;
using System.Data;
using CSharp_ChildrenCompetitionGUI.model;
using log4net;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public class TestParticipantRelationDbRepository : ITestParticipantRelationRepository<Tuple<int, int>, TestParticipantRelation>
    {
        
        private static readonly ILog log = LogManager.GetLogger("UserDbRepository");

        private IDictionary<String, string> props;

        public TestParticipantRelationDbRepository(IDictionary<String, string> props)
        {
            log.Info("Creating TestParticipantRelationRepository");
            this.props = props;
        }

        public int size()
        {
            throw new NotImplementedException();
        }

        public void save(TestParticipantRelation entity)
        {
            // throw new NotImplementedException();
            var conn = DBUtils.getConnection(props);

            using (var command = conn.CreateCommand())
            {
                command.CommandText = "INSERT into test_participant_relation(id_test, id_participant) values (@idt, @idp)";
                var paramIDT = command.CreateParameter();
                paramIDT.ParameterName = "@idt";
                paramIDT.Value = entity.id.Item1;
                command.Parameters.Add(paramIDT);

                var paramIDP = command.CreateParameter();
                paramIDP.ParameterName = "@idp";
                paramIDP.Value = entity.id.Item2;
                command.Parameters.Add(paramIDP);

                var result = command.ExecuteNonQuery();
            }
        }

        public void delete(Tuple<int, int> id)
        {
            throw new NotImplementedException();
        }

        public void update(Tuple<int, int> id, TestParticipantRelation entity)
        {
            throw new NotImplementedException();
        }

        public TestParticipantRelation findOne(Tuple<int, int> id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<TestParticipantRelation> findAll()
        {
            throw new NotImplementedException();
        }

        // public IEnumerable<int> findAllParticipantsIDForTest(int id)
        // {
        //     // throw new NotImplementedException();
        //     IDbConnection con = DBUtils.getConnection(props);
        //     IList<int> participantIdList = new List<int>();
        //     using (var comm = con.CreateCommand())
        //     {
        //         comm.CommandText = "SELECT * from test_participant_relation where id_test = @idt";
        //         
        //         var paramIDT = comm.CreateParameter();
        //         paramIDT.ParameterName = "@idt";
        //         paramIDT.Value = id;
        //         comm.Parameters.Add(paramIDT);
        //
        //         using (var dataR = comm.ExecuteReader())
        //         {
        //             while (dataR.Read())
        //             {
        //                 int idP = dataR.GetInt32(1);
        //                 participantIdList.Add(idP);
        //             }
        //         }
        //     }
        //
        //     return participantIdList;
        // }
    }
}