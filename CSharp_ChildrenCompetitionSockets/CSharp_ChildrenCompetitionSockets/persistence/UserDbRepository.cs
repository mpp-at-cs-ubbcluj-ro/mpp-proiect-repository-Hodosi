using System;
using System.Collections.Generic;
using System.Data;
using CSharp_ChildrenCompetitionGUI.model;
// using log4net;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public class UserDbRepository : IUserRepository
    {
        private User currentUser;
        
        // private static readonly ILog log = LogManager.GetLogger("UserDbRepository");

        // private IDictionary<String, string> props;

        public UserDbRepository(IDictionary<String, string> props)
        {
            // log.Info("Creating UserDbRepository");
            // this.props = props;
        }
        
        public void setCurrentUser(User entity)
        {
            // throw new System.NotImplementedException();
            this.currentUser = entity;
        }

        public User getCurrentUser()
        {
            // throw new System.NotImplementedException();
            return this.currentUser;
        }
        
        public int size()
        {
            throw new System.NotImplementedException();
        }

        public void save(User entity)
        {
            // throw new System.NotImplementedException();
            var conn = DBUtils.getConnection();

            using (var command = conn.CreateCommand())
            {
                command.CommandText = "INSERT into users(firstname, lastname, username, password) values (@fn,@ln,@un,@pw)";
                var paramFN = command.CreateParameter();
                paramFN.ParameterName = "@fn";
                paramFN.Value = entity.firstname;
                command.Parameters.Add(paramFN);

                var paramLN = command.CreateParameter();
                paramLN.ParameterName = "@ln";
                paramLN.Value = entity.lastname;
                command.Parameters.Add(paramLN);

                var paramUN = command.CreateParameter();
                paramUN.ParameterName = "@un";
                paramUN.Value = entity.username;
                command.Parameters.Add(paramUN);

                var paramPW = command.CreateParameter();
                paramPW.ParameterName = "@pw";
                paramPW.Value = entity.password;
                command.Parameters.Add(paramPW);

                var result = command.ExecuteNonQuery();
            }
        }

        public void delete(int id)
        {
            throw new System.NotImplementedException();
        }

        public void update(int id, User entity)
        {
            throw new System.NotImplementedException();
        }

        public User findOne(int id)
        {
            // throw new System.NotImplementedException();
            // log.InfoFormat("Find one with value {0}", id);
            IDbConnection conn = DBUtils.getConnection();

            using (var comm = conn.CreateCommand())
            {
                comm.CommandText = "SELECT * from users where id_user = @id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idU = dataR.GetInt32(0);
                        String firstname = dataR.GetString(1);
                        String lastname = dataR.GetString(2);
                        String username = dataR.GetString(3);
                        String password = dataR.GetString(4);
                        User user = new User(firstname, lastname, username, password);
                        user.id = idU;
                        // log.InfoFormat("Exiting findOne with value{0}", user);
                        return user;
                    }
                }
            }
            // log.InfoFormat("exiting findOne with value{0}", null);
            return null;

        }

        public IEnumerable<User> findAll()
        {
            throw new System.NotImplementedException();
        }

        public User findByUsername(string username)
        {
            // throw new System.NotImplementedException();
            // throw new System.NotImplementedException();
            // log.InfoFormat("Find one with value {0}", username);
            IDbConnection conn = DBUtils.getConnection();

            using (var comm = conn.CreateCommand())
            {
                comm.CommandText = "SELECT * from users where username = @us";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@us";
                paramId.Value = username;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        String firstname = dataR.GetString(1);
                        String lastname = dataR.GetString(2);
                        String usernameU = dataR.GetString(3);
                        String password = dataR.GetString(4);
                        User user = new User(firstname, lastname, usernameU, password);
                        user.id = id;
                        // log.InfoFormat("Exiting findOne with value{0}", user);
                        return user;
                    }
                }
            }
            // log.InfoFormat("exiting findOne with value{0}", null);
            return null;
        }
    }
}