using System;
using System.Collections.Generic;
using System.Data;
using CSharp_ChildrenCompetitionGUI.model;
// using log4net;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public class TestTypeDbRepository : ITestTypeRepository
    {
        // private static readonly ILog log = LogManager.GetLogger("UserDbRepository");

        private IDictionary<String, string> props;

        public TestTypeDbRepository(IDictionary<String, string> props)
        {
            // log.Info("Creating TestTypeDbRepository");
            this.props = props;
        }
        
        public int size()
        {
            throw new System.NotImplementedException();
        }

        public void save(TestType entity)
        {
            throw new System.NotImplementedException();
        }

        public void delete(int id)
        {
            throw new System.NotImplementedException();
        }

        public void update(int id, TestType entity)
        {
            throw new System.NotImplementedException();
        }

        public TestType findOne(int id)
        {
            // throw new System.NotImplementedException();
            // log.InfoFormat("Find one with value {0}", id);
            IDbConnection conn = DBUtils.getConnection();

            using (var comm = conn.CreateCommand())
            {
                comm.CommandText = "SELECT * from test_type where id_test_type = @id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idT = dataR.GetInt32(0);
                        String type = dataR.GetString(1);
                        TestType testType = new TestType(type);
                        testType.id = idT;
                        // log.InfoFormat("Exiting findOne with value{0}", testType);
                        return testType;
                    }
                }
            }
            // log.InfoFormat("exiting findOne with value{0}", null);
            return null;
        }

        public IEnumerable<TestType> findAll()
        {
            throw new System.NotImplementedException();
        }
    }
}