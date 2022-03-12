using System;
using System.Collections.Generic;
using System.Data;
using CSharp_ChildrenCompetitionGUI.model;
using log4net;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public class TestDbRepository : ITestRepository<int, Test>
    {
        
        private static readonly ILog log = LogManager.GetLogger("UserDbRepository");

        private IDictionary<String, string> props;

        public TestDbRepository(IDictionary<String, string> props)
        {
            log.Info("Creating TestDbRepository");
            this.props = props;
        }

        public int size()
        {
            throw new System.NotImplementedException();
        }

        public void save(Test entity)
        {
            throw new System.NotImplementedException();
        }

        public void delete(int id)
        {
            throw new System.NotImplementedException();
        }

        public void update(int id, Test entity)
        {
            throw new System.NotImplementedException();
        }

        public TestType findOneTestType(int id)
        {
            log.InfoFormat("Find one with value {0}", id);
            IDbConnection conn = DBUtils.getConnection(props);

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
                        log.InfoFormat("Exiting findOne with value{0}", testType);
                        return testType;
                    }
                }
            }
            log.InfoFormat("exiting findOne with value{0}", null);
            return null;
        }

        public TestAgeCategory findOneTestAgeCategory(int id)
        {
            log.InfoFormat("Find one with value {0}", id);
            IDbConnection conn = DBUtils.getConnection(props);

            using (var comm = conn.CreateCommand())
            {
                comm.CommandText = "SELECT * from test_age_category where id_test_age_category = @id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idT = dataR.GetInt32(0);
                        int minAge = dataR.GetInt32(1);
                        int maxAge = dataR.GetInt32(2);
                        TestAgeCategory testAgeCategory = new TestAgeCategory(minAge, maxAge);
                        testAgeCategory.id = idT;
                        log.InfoFormat("Exiting findOne with value{0}", testAgeCategory);
                        return testAgeCategory;
                    }
                }
            }
            log.InfoFormat("exiting findOne with value{0}", null);
            return null;
        }

        public Test findOne(int id)
        {
            // throw new System.NotImplementedException();
            log.InfoFormat("Find one with value {0}", id);
            IDbConnection conn = DBUtils.getConnection(props);

            using (var comm = conn.CreateCommand())
            {
                comm.CommandText = "SELECT * from tests where id_test = @id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idT = dataR.GetInt32(0);
                        int idTT = dataR.GetInt32(1);
                        int idTAC = dataR.GetInt32(2);
                        TestType testType = findOneTestType(idTT);
                        TestAgeCategory testAgeCategory = findOneTestAgeCategory(idTAC);
                        Test test = new Test(testType, testAgeCategory);
                        test.id = idT;
                        log.InfoFormat("Exiting findOne with value{0}", test);
                        return test;
                    }
                }
            }
            log.InfoFormat("exiting findOne with value{0}", null);
            return null;
        }

        public IEnumerable<Test> findAll()
        {
            // throw new System.NotImplementedException();
            IDbConnection con = DBUtils.getConnection(props);
            IList<Test> testList = new List<Test>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "SELECT * from tests";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idT = dataR.GetInt32(0);
                        int idTT = dataR.GetInt32(1);
                        int idTAC = dataR.GetInt32(2);
                        TestType testType = findOneTestType(idTT);
                        TestAgeCategory testAgeCategory = findOneTestAgeCategory(idTAC);
                        Test test = new Test(testType, testAgeCategory);
                        test.id = idT;
                        testList.Add(test);
                    }
                }
            }

            return testList;
        }
    }
}