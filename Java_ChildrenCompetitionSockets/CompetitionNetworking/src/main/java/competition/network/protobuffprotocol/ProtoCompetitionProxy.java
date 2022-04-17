package competition.network.protobuffprotocol;

import competition.model.*;
import competition.network.rpcprotocol.Request;
import competition.network.rpcprotocol.RequestType;
import competition.network.rpcprotocol.Response;
import competition.network.rpcprotocol.ResponseType;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoCompetitionProxy implements ICompetitionServices {

    private String host;
    private int port;

    private ICompetitionObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<CompetitionProtobufs.CompetitionResponse> qresponses;
    private volatile boolean finished;
    public ProtoCompetitionProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<CompetitionProtobufs.CompetitionResponse>();
    }

    @Override
    public void login(User user, ICompetitionObserver client) throws CompetitionException {
        System.out.println("PROXY - LOGIN");
        initializeConnection();
//        Request req=new Request.Builder().type(RequestType.LOGIN).data(user).build();
        sendRequest(ProtoUtils.createLoginRequest(user));
        CompetitionProtobufs.CompetitionResponse response=readResponse();
        if (response.getType() == CompetitionProtobufs.CompetitionResponse.Type.Ok){
            this.client=client;
            return;
        }
        if (response.getType() == CompetitionProtobufs.CompetitionResponse.Type.Error) {
//            String err=response.data().toString();
            String err= ProtoUtils.getError(response);
            closeConnection();
            throw new CompetitionException(err);
        }
    }

    @Override
    public void logout(User user, ICompetitionObserver client) throws CompetitionException {
        System.out.println("PROXY - LOGOUT");
//        Request req=new Request.Builder().type(RequestType.LOGOUT).data(user).build();
        sendRequest(ProtoUtils.createLogoutRequest(user));
        CompetitionProtobufs.CompetitionResponse response=readResponse();
        closeConnection();
        if (response.getType() == CompetitionProtobufs.CompetitionResponse.Type.Error){
            String err= ProtoUtils.getError(response);
            throw new CompetitionException(err);
        }
    }

    @Override
    public User findUserByUsername(String username) throws CompetitionException {
        System.out.println("PROXY - FIND BY USERNAME");
        User userDTO = new User("test", "test", username, "test");
        sendRequest(ProtoUtils.createFindUserByUsernameRequest(userDTO));
        CompetitionProtobufs.CompetitionResponse response=readResponse();

        if (response.getType() == CompetitionProtobufs.CompetitionResponse.Type.Error){
            String err= ProtoUtils.getError(response);
            throw new CompetitionException(err);
        }
        User user = ProtoUtils.getUser(response);
        return user;
    }

    @Override
    public Participant findParticipantByUsername(String username) throws CompetitionException {
        System.out.println("PROXY - FIND PARTICIPANT BY USERNAME");
        Participant participantDTO = new Participant(username, "test", 1000);
        participantDTO.setId(0);
        sendRequest(ProtoUtils.createFindParticipantByUsernameRequest(participantDTO));
        CompetitionProtobufs.CompetitionResponse response=readResponse();
        if (response.getType() == CompetitionProtobufs.CompetitionResponse.Type.Error){
            String err= ProtoUtils.getError(response);
            throw new CompetitionException(err);
        }
        Participant participant = ProtoUtils.getParticipant(response);
        return participant;
    }

    @Override
    public void saveParticipant(String username, String name, int age, int testId) throws CompetitionException {
        System.out.println("PROXY - SAVE PARTICIPANT");
        Participant participant = new Participant(username, name, age);
        participant.setId(testId);
        sendRequest(ProtoUtils.createSaveParticipantRequest(participant));
        CompetitionProtobufs.CompetitionResponse response=readResponse();
        if (response.getType() == CompetitionProtobufs.CompetitionResponse.Type.Error){
            String err= ProtoUtils.getError(response);
            throw new CompetitionException(err);
        }
    }

    @Override
    public void saveRelation(int idTest, int idParticipant) throws CompetitionException {
        System.out.println("PROXY - SAVE RELATION");
        Tuple<Integer, Integer> id = new Tuple<>(idTest, idParticipant);
        TestParticipantRelation testParticipantRelation = new TestParticipantRelation(id);
        sendRequest(ProtoUtils.createSaveRelationRequest(testParticipantRelation));
        CompetitionProtobufs.CompetitionResponse response=readResponse();
        if (response.getType() == CompetitionProtobufs.CompetitionResponse.Type.Error){
            String err= ProtoUtils.getError(response);
            throw new CompetitionException(err);
        }
    }

    @Override
    public User[] getLoggedUsers(User user) throws CompetitionException {
        return new User[0];
    }

    @Override
    public TestDTO[] findAllTestDTOs() throws CompetitionException {
        System.out.println("PROXY - FIND ALL TEST DTOs");
        sendRequest(ProtoUtils.createFindAllTestDTOsRequest());
        CompetitionProtobufs.CompetitionResponse response=readResponse();
        if (response.getType() == CompetitionProtobufs.CompetitionResponse.Type.Error){
            String err= ProtoUtils.getError(response);
            throw new CompetitionException(err);
        }
        TestDTO[] testDTOS=ProtoUtils.getTestDTOs(response);
        return testDTOS;
    }

    @Override
    public Participant[] findAllParticipantsForTest(Integer id) throws CompetitionException {
        System.out.println("PROXY - FIND ALL PARTICIPANT FOR TEST");
        Test tesDTO = new Test(null, null);
        tesDTO.setId(id);
        System.out.println("-------------------------------------");
        System.out.println(id);
        System.out.println("-------------------------------------");

        sendRequest(ProtoUtils.createFindAllParticipantsForTestRequest(tesDTO));
        CompetitionProtobufs.CompetitionResponse response=readResponse();
        if (response.getType() == CompetitionProtobufs.CompetitionResponse.Type.Error){
            String err= ProtoUtils.getError(response);
            throw new CompetitionException(err);
        }
        Participant[] participants=ProtoUtils.getParticipants(response);
        return participants;
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(CompetitionProtobufs.CompetitionRequest request)throws CompetitionException{
        try {
            System.out.println("Sending request ..."+request);
            //request.writeTo(output);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            throw new CompetitionException("Error sending object "+e);
        }

    }

    private CompetitionProtobufs.CompetitionResponse readResponse() throws CompetitionException{
        CompetitionProtobufs.CompetitionResponse response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws CompetitionException{
        try {
            connection=new Socket(host,port);
            output=connection.getOutputStream();
            //output.flush();
            input=connection.getInputStream();     //new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(CompetitionProtobufs.CompetitionResponse updateResponse){
        switch (updateResponse.getType()){
            //todo
            case NewParticipant: {
                System.out.println("new participant ");
                try{
                    client.participantSaved();
                }catch (CompetitionException exception){
                    exception.printStackTrace();
                }
            }
        }

    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.parseDelimitedFrom(input);
                    System.out.println("response received "+response);

                    if (isUpdateResponse(response.getType())){
                        handleUpdate(response);
                    }else{
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }

    private boolean isUpdateResponse(CompetitionProtobufs.CompetitionResponse.Type type){
        switch (type){
            case NewParticipant: return true;
        }
        return false;
    }
}
