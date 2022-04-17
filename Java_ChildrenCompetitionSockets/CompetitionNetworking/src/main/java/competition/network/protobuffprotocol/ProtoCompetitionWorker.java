package competition.network.protobuffprotocol;

import competition.model.*;
import competition.network.rpcprotocol.Response;
import competition.network.rpcprotocol.ResponseType;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProtoCompetitionWorker implements Runnable, ICompetitionObserver {
    private ICompetitionServices server;
    private Socket connection;

    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;
    public ProtoCompetitionWorker(ICompetitionServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=connection.getOutputStream() ;//new ObjectOutputStream(connection.getOutputStream());
            input=connection.getInputStream(); //new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while(connected){
            try {
                // Object request=input.readObject();
                System.out.println("Waiting requests ...");
                CompetitionProtobufs.CompetitionRequest request=CompetitionProtobufs.CompetitionRequest.parseDelimitedFrom(input);
                System.out.println("Request received: "+request);
                CompetitionProtobufs.CompetitionResponse response=handleRequest(request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    @Override
    public void userLoggedIn(User user) throws CompetitionException {

    }

    @Override
    public void userLoggedOut(User user) throws CompetitionException {

    }

    @Override
    public void participantSaved() throws CompetitionException {
        System.out.println("WORKER - PARTICIPANT SAVED");
        System.out.println("Participant saved");
        try {
            sendResponse(ProtoUtils.createNewParticipantResponse());
        } catch (IOException e) {
            throw new CompetitionException("Sending error: "+e);
        }
    }

    private CompetitionProtobufs.CompetitionResponse handleRequest(CompetitionProtobufs.CompetitionRequest request){
        CompetitionProtobufs.CompetitionResponse response=null;
        switch (request.getType()){
            case Login:{
                System.out.println("Login request ...");
                User user= ProtoUtils.getUser(request);
                try {
                    server.login(user, this);
                    return ProtoUtils.createOkResponse();

                }
                catch (CompetitionException e) {
                    connected=false;
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case Logout:{
                System.out.println("Logout request");
                User user= ProtoUtils.getUser(request);
                try {
                    server.logout(user, this);
                    connected=false;
                    return ProtoUtils.createOkResponse();

                }
                catch (CompetitionException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }

            case FindUserByUsername:{
                System.out.println("Find by user by username request");
                User userDTO= ProtoUtils.getUser(request);
                try{
                    User user = server.findUserByUsername(userDTO.getUsername());
                    return ProtoUtils.createFindByUsernameResponse(user);

                }
                catch (CompetitionException e){
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }

            case SaveParticipant:{
                System.out.println("Save participant request");
                Participant participant = ProtoUtils.getParticipant(request);
                try{
                    server.saveParticipant(participant.getUsername(), participant.getName(), participant.getAge(), participant.getId());
                    return ProtoUtils.createOkResponse();
                }
                catch (CompetitionException e){
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }

            case FindAllTestDto:{
                System.out.println("Find all test dto request");
                try{
                    TestDTO[] testDTOs = server.findAllTestDTOs();
                    return ProtoUtils.createFindAllTestDTOsResponse(testDTOs);

                }
                catch (CompetitionException e){
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }

            case SaveRelation:{
                System.out.println("Save relation request");
                try {
                    TestParticipantRelation testParticipantRelation = ProtoUtils.getTestParticipantRelation(request);
                    server.saveRelation(testParticipantRelation.getId().getLeft(), testParticipantRelation.getId().getRight());
                    return ProtoUtils.createOkResponse();
                }
                catch (CompetitionException e){
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }

            case FindParticipantByUsername:{
                System.out.println("Find participant by username request");
                Participant participantDTO = ProtoUtils.getParticipant(request);
                try {
                    Participant participant = server.findParticipantByUsername(participantDTO.getUsername());
                    return ProtoUtils.createFindParticipantByUsernameResponse(participant);

                }
                catch (CompetitionException e){
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }

            case FinAllParticipantsForTest:{
                System.out.println("Find all test dto request");
                Test testDTO = ProtoUtils.getTest(request);
                try{
                    Participant[] participants = server.findAllParticipantsForTest(testDTO.getId());
                    return ProtoUtils.createFindAllParticipantForTestResponse(participants);

                }
                catch (CompetitionException e){
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
        }

        return response;
    }

    private void sendResponse(CompetitionProtobufs.CompetitionResponse response) throws IOException{
        System.out.println("sending response "+response);
        response.writeDelimitedTo(output);
        //output.writeObject(response);
        output.flush();
    }
}
