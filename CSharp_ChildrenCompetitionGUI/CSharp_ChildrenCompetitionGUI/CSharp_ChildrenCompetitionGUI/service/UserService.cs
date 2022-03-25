using CSharp_ChildrenCompetition.repository;
using CSharp_ChildrenCompetitionGUI.model;
using CSharp_ChildrenCompetitionGUI.repository;

namespace CSharp_ChildrenCompetitionGUI.service
{
    public class UserService : IUserService<int, User>
    {
        public IUserRepository<int, User> userRepository;

        public UserService(IUserRepository<int, User> userRepository)
        {
            this.userRepository = userRepository;
        }

        public void login(string username, string password)
        {
            // throw new System.NotImplementedException();
            User user = userRepository.findByUsername(username);
            if (user != null)
            {
                if (!user.password.Equals(password))
                {
                    throw new WrongPasswordException();
                }
                userRepository.setCurrentUser(user);
            }
            else
            {
                throw new WrongUsernameException();
            }
        }

        public void logout()
        {
            // throw new System.NotImplementedException();
            userRepository.setCurrentUser(null);
        }

        public User getCurrentUser()
        {
            // throw new System.NotImplementedException();
            return userRepository.getCurrentUser();
        }
    }
}