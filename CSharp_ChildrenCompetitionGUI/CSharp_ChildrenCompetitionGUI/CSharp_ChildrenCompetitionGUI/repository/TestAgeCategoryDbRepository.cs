using System;
using System.Collections.Generic;
using System.Data;
using CSharp_ChildrenCompetitionGUI.model;
using log4net;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public class TestAgeCategoryDbRepository : ITestAgeCategoryRepository<int, TestAgeCategory>
    {
        private static readonly ILog log = LogManager.GetLogger("UserDbRepository");

        public TestAgeCategoryDbRepository(IDictionary<String, string> props)
        {
            log.Info("Creating TestAgeCategoryDbRepository");
            this.props = props;
        }

        private IDictionary<String, string> props;
        
        public int size()
        {
            throw new System.NotImplementedException();
        }

        public void save(TestAgeCategory entity)
        {
            throw new System.NotImplementedException();
        }

        public void delete(int id)
        {
            throw new System.NotImplementedException();
        }

        public void update(int id, TestAgeCategory entity)
        {
            throw new System.NotImplementedException();
        }

        public TestAgeCategory findOne(int id)
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

        public IEnumerable<TestAgeCategory> findAll()
        {
            throw new System.NotImplementedException();
        }
    }
}