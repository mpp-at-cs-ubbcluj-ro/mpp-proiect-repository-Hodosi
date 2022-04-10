using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using CSharp_ChildrenCompetitionGUI.model;
using services;

namespace networking
{
    public class CompetitionServerProxy : ICompetitionServices
    {
        private string host;
        private int port;

        private ICompetitionObserver client;

        private NetworkStream stream;
		
        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;
        public CompetitionServerProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses=new Queue<Response>();
        }
        
        
        public void login(User user, ICompetitionObserver client)
        {
            // throw new System.NotImplementedException();
            initializeConnection();
            Request request = new Request.Builder().type(RequestType.LOGIN).data(user).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type.Equals(ResponseType.OK))
            {
	            this.client = client;
	            return;
            }

            if (response.type.Equals(ResponseType.ERROR))
            {
	            string err = response.data.ToString();
	            closeConnection();
	            throw new CompetitionException(err);
            }

        }

        public void logout(User user, ICompetitionObserver client)
        {
            // throw new System.NotImplementedException();
            Request request = new Request.Builder().type(RequestType.LOGOUT).data(user).build();
            sendRequest(request);
            Response response = readResponse();
            closeConnection();
            if (response.type.Equals(ResponseType.ERROR))
            {
	            string err = response.data.ToString();
	            throw new CompetitionException(err);
            }

        }

        public User findUserByUsername(string username)
        {
            // throw new System.NotImplementedException();
            User userDTO = new User("test", "test", username, "test");
            Request request = new Request.Builder().type(RequestType.FIND_USER_BY_USERNAME).data(userDTO).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type.Equals(ResponseType.ERROR))
            {
	            string err = response.data.ToString();
	            throw new CompetitionException(err);
            }

            User user = (User) response.data;
            return user;
        }

        public Participant findParticipantByUsername(string username)
        {
            // throw new System.NotImplementedException();
            Participant participantDTO = new Participant(username,"test", 1000);
            Request request = new Request.Builder().type(RequestType.FIND_PARTICIPANT_BY_USERNAME).data(participantDTO).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type.Equals(ResponseType.ERROR))
            {
	            string err = response.data.ToString();
	            throw new CompetitionException(err);
            }
	        Participant participant = (Participant) response.data;
            return participant;
        }

        public void saveParticipant(string username, string name, int age, int testId)
        {
            // throw new System.NotImplementedException();
            Participant participant = new Participant(username, name, age);
            participant.id = testId;
            Request request = new Request.Builder().type(RequestType.SAVE_PARTICIPANT).data(participant).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type.Equals(ResponseType.ERROR))
            {
	            string err = response.data.ToString();
	            throw new CompetitionException(err);
            }

        }

        public void saveRelation(int idTest, int idParticipant)
        {
            // throw new System.NotImplementedException();
            Tuple<int, int> id = new Tuple<int, int>(idTest, idParticipant);
            TestParticipantRelation testParticipantRelation = new TestParticipantRelation(id);
            Request request = new Request.Builder().type(RequestType.SAVE_RELATION).data(testParticipantRelation).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type.Equals(ResponseType.ERROR))
            {
	            string err = response.data.ToString();
	            throw new CompetitionException(err);
            }
        }

        public User[] getLoggedUsers(User user)
        {
            // throw new System.NotImplementedException();
            Request request = new Request.Builder().type(RequestType.GET_LOGGED_USERS).data(user).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type.Equals(ResponseType.ERROR))
            {
	            string err = response.data.ToString();
	            throw new CompetitionException(err);
            }
            User[] users=(User[])response.data;
            return users;
        }

        public TestDTO[] findAllTestDTOs()
        {
            // throw new System.NotImplementedException();
            Request request = new Request.Builder().type(RequestType.FIND_ALL_TEST_DTO).data(null).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type.Equals(ResponseType.ERROR))
            {
	            string err = response.data.ToString();
	            throw new CompetitionException(err);
            }
            TestDTO[] testDTOS=(TestDTO[])response.data;
            return testDTOS;
        }

        public Participant[] findAllParticipantsForTest(int id)
        {
            // throw new System.NotImplementedException();
            Test testDTO = new Test(null, null);
            testDTO.id = id;
            Request request = new Request.Builder().type(RequestType.FIND_ALL_PARTICIPANTS_FOR_TEST).data(testDTO).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type.Equals(ResponseType.ERROR))
            {
	            string err = response.data.ToString();
	            throw new CompetitionException(err);
            }
            Participant[] participants=(Participant[])response.data;
            return participants;
        }
        
	    private void closeConnection()
		{
			finished=true;
			try
			{
				stream.Close();
			
				connection.Close();
                _waitHandle.Close();
				client=null;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}

		}

		private void sendRequest(Request request)
		{
			try
			{
                formatter.Serialize(stream, request);
                stream.Flush();
			}
			catch (Exception e)
			{
				throw new CompetitionException("Error sending object " + e);
			}

		}

		private Response readResponse()
		{
			Response response =null;
			try
			{
                _waitHandle.WaitOne();
				lock (responses)
				{
                    //Monitor.Wait(responses); 
                    response = responses.Dequeue();
                
				}
				

			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
			return response;
		}
		
		private void initializeConnection()
		{
			 try
			 {
				connection=new TcpClient(host,port);
				stream=connection.GetStream();
                formatter = new BinaryFormatter();
				finished=false;
                _waitHandle = new AutoResetEvent(false);
				startReader();
			}
			catch (Exception e)
			{
                Console.WriteLine(e.StackTrace);
			}
		}
		private void startReader()
		{
			Thread tw =new Thread(run);
			tw.Start();
		}


		private void handleUpdate(Response response)
		{
			if (response.type.Equals(ResponseType.USER_LOGGED_IN))
			{
				User user = (User)response.data;
				Console.WriteLine("User logged in "+ user);
				try
				{
					client.userLoggedIn(user);
				}
				catch (CompetitionException e)
				{
                    Console.WriteLine(e.StackTrace); 
				}
			}
			if (response.type.Equals(ResponseType.USER_LOGGED_OUT))
			{
				User user = (User)response.data;
				Console.WriteLine("User logged out "+ user);
				try
				{
					client.userLoggedOut(user);
				}
				catch (CompetitionException e)
				{
					Console.WriteLine(e.StackTrace); 
				}
			}

			if (response.type.Equals(ResponseType.NEW_PARTICIPANT))
			{
				Console.WriteLine("new participant");
				try
				{
					client.participantSaved();
				}
				catch (CompetitionException e)
				{
                    Console.WriteLine(e.StackTrace);
				}
			}
		}

		private bool isUpdate(Response response)
		{
			return response.type.Equals(ResponseType.USER_LOGGED_IN) ||
			       response.type.Equals(ResponseType.USER_LOGGED_OUT) ||
			       response.type.Equals(ResponseType.NEW_PARTICIPANT);
		}
		
		public virtual void run()
		{
			while(!finished)
			{
				try
				{
					object response = formatter.Deserialize(stream);
					Console.WriteLine("response received "+response);
					if (isUpdate((Response)response))
					{
						handleUpdate((Response)response);
					}
					else
					{
							
						lock (responses)
						{
                                					
								 
							responses.Enqueue((Response)response);
                               
						}
						_waitHandle.Set();
					}
				}
				catch (Exception e)
				{
					Console.WriteLine("Reading error "+e);
				}
			}
	    }
    }
}