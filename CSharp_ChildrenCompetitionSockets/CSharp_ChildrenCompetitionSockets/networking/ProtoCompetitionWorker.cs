using System;
using System.Net.Sockets;
using System.Threading;
using Chat.Protocol;
using Google.Protobuf;
using services;

using modelOriginal = CSharp_ChildrenCompetitionGUI.model;

namespace networking
{
    public class ProtoCompetitionWorker : ICompetitionObserver
    {
        private ICompetitionServices server;
		private TcpClient connection;

		private NetworkStream stream;
		private volatile bool connected;
		public ProtoCompetitionWorker(ICompetitionServices server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{
				
				stream=connection.GetStream();
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
					
                    CompetitionRequest request = CompetitionRequest.Parser.ParseDelimitedFrom(stream);
					CompetitionResponse response =handleRequest(request);
					if (response!=null)
					{
					   sendResponse(response);
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
        
		//aici baga cod
		
		public void userLoggedIn(modelOriginal.User user)
		{
			throw new NotImplementedException();
		}

		public void userLoggedOut(modelOriginal.User user)
		{
			throw new NotImplementedException();
		}

		public void participantSaved()
		{
			// throw new NotImplementedException();
			try {
				sendResponse(ProtoUtils.createNewParticipantResponse());
			} catch (Exception e) {
				throw new CompetitionException("Sending error: "+e);
			}
		}

		private CompetitionResponse handleRequest(CompetitionRequest request)
		{
			CompetitionResponse response =null;
            CompetitionRequest.Types.Type reqType=request.Type;
            switch(reqType){
                case CompetitionRequest.Types.Type.Login:
                {
	                Console.WriteLine("Login request ...");
	                modelOriginal.User user = ProtoUtils.getUser(request);
	                try
	                {
		                lock (server)
		                {
			                server.login(user, this);
		                }

		                return ProtoUtils.createOkResponse();
	                }
	                catch (CompetitionException e)
	                {
		                connected=false;
		                return ProtoUtils.createErrorResponse(e.Message);
	                }
                }
                case CompetitionRequest.Types.Type.Logout:
				{
					Console.WriteLine("Logout request ...");
					modelOriginal.User user = ProtoUtils.getUser(request);
					try
					{
						lock (server)
						{
							server.logout(user, this);
						}
						connected=false;
						return ProtoUtils.createOkResponse();
					}
					catch (CompetitionException e)
					{
						return ProtoUtils.createErrorResponse(e.Message);
					}
				}
                case CompetitionRequest.Types.Type.FindUserByUsername:
                {
	                Console.WriteLine("Find user by username request ...");
	                modelOriginal.User userDTO = ProtoUtils.getUser(request);
	                try
	                {
		                modelOriginal.User user;
		                lock (server)
		                {
			                user = server.findUserByUsername(userDTO.username);
		                }
		                return ProtoUtils.createFindByUsernameResponse(user);
	                }
	                catch (CompetitionException e)
	                {
		                return ProtoUtils.createErrorResponse(e.Message);
	                }
                }
                case CompetitionRequest.Types.Type.SaveParticipant:
                {
	                Console.WriteLine("Save participant request ...");
	                modelOriginal.Participant participant = ProtoUtils.getParticipant(request);
	                try
	                {
		                lock (server)
		                {
			                server.saveParticipant(participant.username, participant.name, participant.age, participant.id);
		                }
		                return ProtoUtils.createOkResponse();
	                }
	                catch (CompetitionException e)
	                {
		                return ProtoUtils.createErrorResponse(e.Message);
	                }
                }
                case CompetitionRequest.Types.Type.FindAllTestDto:
                {
	                Console.WriteLine("Find all test dto request ...");
	                try
	                {
		                modelOriginal.TestDTO[] testDTOs;
		                lock (server)
		                {
			                testDTOs = server.findAllTestDTOs();
		                }
		                return ProtoUtils.createFindAllTestDTOsResponse(testDTOs);
	                }
	                catch (CompetitionException e)
	                {
		                return ProtoUtils.createErrorResponse(e.Message);
	                }
                }
                case CompetitionRequest.Types.Type.SaveRelation:
                {
	                Console.WriteLine("Save relation request ...");
	                modelOriginal.TestParticipantRelation testParticipantRelation = ProtoUtils.getTestParticipantRelation(request);
	                try
	                {
		                lock (server)
		                {
			                server.saveRelation(testParticipantRelation.id.Item1, testParticipantRelation.id.Item2);
		                }
		                return ProtoUtils.createOkResponse();
	                }
	                catch (CompetitionException e)
	                {
		                return ProtoUtils.createErrorResponse(e.Message);
	                }
                }
                case CompetitionRequest.Types.Type.FindParticipantByUsername:
                {
	                Console.WriteLine("Find participant by username request ...");
	                modelOriginal.Participant participantDTO = ProtoUtils.getParticipant(request);
	                try
	                {
		                modelOriginal.Participant participant;
		                lock (server)
		                {
			                participant = server.findParticipantByUsername(participantDTO.username);
		                }
		                return ProtoUtils.createFindParticipantByUsernameResponse(participant);
	                }
	                catch (CompetitionException e)
	                {
		                return ProtoUtils.createErrorResponse(e.Message);
	                }
                }
                case CompetitionRequest.Types.Type.FinAllParticipantsForTest:
                {
	                Console.WriteLine("Find all participants for test request ...");
	                modelOriginal.Test testDTO = ProtoUtils.getTest(request);
	                try
	                {
		                modelOriginal.Participant[] participants;
		                lock (server)
		                {
			                participants = server.findAllParticipantsForTest(testDTO.id);
		                }

		                return ProtoUtils.createFindAllParticipantForTestResponse(participants);
	                }
	                catch (CompetitionException e)
	                {
		                return ProtoUtils.createErrorResponse(e.Message);
	                }
                }
 
            }
			return response;
		}

		private void sendResponse(CompetitionResponse response)
		{
			Console.WriteLine("sending response " + response);
			lock (stream)
			{
				response.WriteDelimitedTo(stream);
				stream.Flush();
			}

		}
    }
}