using System;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using CSharp_ChildrenCompetitionGUI.model;
using services;

namespace networking
{
    public class CompetitionClientWorker : ICompetitionObserver
    {
        private ICompetitionServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;
        public CompetitionClientWorker(ICompetitionServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
				
                stream=connection.GetStream();
                formatter = new BinaryFormatter();
                connected=true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while(connected)
            {
                try
                {
                    object request = formatter.Deserialize(stream);
                    object response =handleRequest((Request)request);
                    if (response!=null)
                    {
                        sendResponse((Response) response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
				
                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error "+e);
            }
        }
        
        public void userLoggedIn(User user)
        {
	        // throw new NotImplementedException();
	        Console.WriteLine("User logged in "+ user);
	        Response response = new Response.Builder().type(ResponseType.USER_LOGGED_IN).data(user).build();
	        try
	        {
		        sendResponse(response);
	        }
	        catch (Exception e)
	        {
		        Console.WriteLine(e.StackTrace);
	        }
        }

        public void userLoggedOut(User user)
        {
	        // throw new NotImplementedException();
	        Console.WriteLine("User logged out" + user);
	        Response response = new Response.Builder().type(ResponseType.USER_LOGGED_OUT).data(user).build();
	        try
	        {
		        sendResponse(response);
	        }
	        catch (Exception e)
	        {
		        Console.WriteLine(e.StackTrace);
	        }
        }

        public void participantSaved()
        {
	        // throw new NotImplementedException();
	        Console.WriteLine("New participant");
	        Response response = new Response.Builder().type(ResponseType.NEW_PARTICIPANT).data(null).build();
	        try
	        {
		        sendResponse(response);
	        }
	        catch (Exception e)
	        {
		        Console.WriteLine(e.StackTrace);
	        }
        }

        private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();
        private Response handleRequest(Request request)
		{
			Response response =null;
			if (request.type.Equals(RequestType.LOGIN))
			{
				Console.WriteLine("Login request ...");
				User user = (User)request.data;
				try
                {
                    lock (server)
                    {
                        server.login(user, this);
                    }

                    return okResponse;
                }
				catch (CompetitionException e)
				{
					connected=false;
					return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
				}
			}

			if (request.type.Equals(RequestType.LOGOUT))
			{
				Console.WriteLine("Logout request ...");
				User user = (User)request.data;
				try
				{
					lock (server)
					{
						server.logout(user, this);
					}
					connected=false;
					return okResponse;
				}
				catch (CompetitionException e)
				{
					return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
				}
			}

			if (request.type.Equals(RequestType.GET_LOGGED_USERS))
			{
				Console.WriteLine("Get logged users request ...");
				User user = (User)request.data;
				try
				{
					User[] users;
					lock (server)
					{
						users = server.getLoggedUsers(user);
					}
					return new Response.Builder().type(ResponseType.GET_LOGGED_USERS).data(users).build();
				}
				catch (CompetitionException e)
				{
					return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
				}
			}


			if (request.type.Equals(RequestType.FIND_USER_BY_USERNAME))
			{
				Console.WriteLine("Find user by username request ...");
				User userDTO = (User) request.data;
				try
				{
					User user;
					lock (server)
					{
						user = server.findUserByUsername(userDTO.username);
					}
					return new Response.Builder().type(ResponseType.FIND_USER_BY_USERNAME).data(user).build();
				}
				catch (CompetitionException e)
				{
					return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
				}
			}

			if (request.type.Equals(RequestType.SAVE_PARTICIPANT))
			{
				Console.WriteLine("Save participant request ...");
				Participant participant = (Participant) request.data;
				try
				{
					lock (server)
					{
						server.saveParticipant(participant.username, participant.name, participant.age, participant.id);
					}
					return okResponse;
				}
				catch (CompetitionException e)
				{
					return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
				}
			}
			
			if (request.type.Equals(RequestType.FIND_ALL_TEST_DTO))
			{
				Console.WriteLine("Find all test dto request ...");
				try
				{
					TestDTO[] testDTOs;
					lock (server)
					{
						testDTOs = server.findAllTestDTOs();
					}
					return new Response.Builder().type(ResponseType.FIND_ALL_TEST_DTO).data(testDTOs).build();
				}
				catch (CompetitionException e)
				{
					return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
				}
			}
			
			if (request.type.Equals(RequestType.SAVE_RELATION))
			{
				Console.WriteLine("Save relation request ...");
				TestParticipantRelation testParticipantRelation = (TestParticipantRelation) request.data;
				try
				{
					lock (server)
					{
						server.saveRelation(testParticipantRelation.id.Item1, testParticipantRelation.id.Item2);
					}
					return okResponse;
				}
				catch (CompetitionException e)
				{
					return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
				}
			}
			
			if (request.type.Equals(RequestType.FIND_PARTICIPANT_BY_USERNAME))
			{
				Console.WriteLine("Find participant by username request ...");
				Participant participantDTO = (Participant) request.data;
				try
				{
					Participant participant;
					lock (server)
					{
						participant = server.findParticipantByUsername(participantDTO.username);
					}
					return new Response.Builder().type(ResponseType.FIND_PARTICIPANT_BY_USERNAME).data(participant).build();
				}
				catch (CompetitionException e)
				{
					return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
				}
			}
			
			if (request.type.Equals(RequestType.FIND_ALL_PARTICIPANTS_FOR_TEST))
			{
				Console.WriteLine("Find all participants for test request ...");
				Test testDTO = (Test) request.data;
				try
				{
					Participant[] participants;
					lock (server)
					{
						participants = server.findAllParticipantsForTest(testDTO.id);
					}
					return new Response.Builder().type(ResponseType.FIND_ALL_PARTICIPANTS_FOR_TEST).data(participants).build();
				}
				catch (CompetitionException e)
				{
					return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
				}
			}

			return response;
		}

		private void sendResponse(Response response)
		{
			Console.WriteLine("sending response "+response);
			lock (stream)
			{
				formatter.Serialize(stream, response);
				stream.Flush();
			}

		}
    }
}