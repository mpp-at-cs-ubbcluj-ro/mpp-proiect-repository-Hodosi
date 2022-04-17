import competition.network.utils.AbstractServer;
import competition.network.utils.CompetitionProtobuffConcurrentServer;
import competition.network.utils.CompetitionRpcConcurrentServer;
import competition.network.utils.ServerException;
import competition.persistence.IParticipantRepository;
import competition.persistence.ITestParticipantRelationRepository;
import competition.persistence.ITestRepository;
import competition.persistence.IUserRepository;
import competition.persistence.repository.jdbc.ParticipantDbRepository;
import competition.persistence.repository.jdbc.TestDbRepository;
import competition.persistence.repository.jdbc.TestParticipantRelationDbRepository;
import competition.persistence.repository.jdbc.UserDbRepository;
import competition.server.CompetitionServicesImplementation;
import competition.services.ICompetitionServices;

import java.io.IOException;
import java.util.Properties;

public class StratProtobuffServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/competitionserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find competitionserver.properties "+e);
            return;
        }
        IUserRepository userRepository = new UserDbRepository(serverProps);
        IParticipantRepository participantRepository = new ParticipantDbRepository(serverProps);
        ITestRepository testRepository = new TestDbRepository(serverProps);
        ITestParticipantRelationRepository testParticipantRelationRepository = new TestParticipantRelationDbRepository(serverProps);
        ICompetitionServices competitionServerImplementation = new CompetitionServicesImplementation(userRepository, participantRepository, testRepository, testParticipantRelationRepository);


        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new CompetitionProtobuffConcurrentServer(chatServerPort, competitionServerImplementation);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
