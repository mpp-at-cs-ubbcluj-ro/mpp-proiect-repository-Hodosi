package competition.network.rpcprotocol;

import competition.model.*;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CompetitionServicesRpcProxy implements ICompetitionServices {
    private String host;
    private int port;

    private ICompetitionObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public CompetitionServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    @Override
    public void login(User user, ICompetitionObserver client) throws CompetitionException {
        System.out.println("PROXY - LOGIN");
        initializeConnection();
        Request req=new Request.Builder().type(RequestType.LOGIN).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new CompetitionException(err);
        }
    }

    @Override
    public void logout(User user, ICompetitionObserver client) throws CompetitionException {
        System.out.println("PROXY - LOGOUT");
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CompetitionException(err);
        }
    }

    @Override
    public User findUserByUsername(String username) throws CompetitionException {
        System.out.println("PROXY - FIND BY USERNAME");
        User userDTO = new User("test", "test", username, "test");
        Request req=new Request.Builder().type(RequestType.FIND_USER_BY_USERNAME).data(userDTO).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CompetitionException(err);
        }
        User user = (User) response.data();
        return user;
    }

    @Override
    public Participant findParticipantByUsername(String username) throws CompetitionException {
        System.out.println("PROXY - FIND PARTICIPANT BY USERNAME");
        Participant participantDTO = new Participant(username, "test", 1000);
        Request req=new Request.Builder().type(RequestType.FIND_PARTICIPANT_BY_USERNAME).data(participantDTO).build();
        sendRequest(req);
        Response response=readResponse();
        System.out.println(response);
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CompetitionException(err);
        }
        Participant participant = (Participant) response.data();
        return participant;
    }

    public void saveParticipant(String username, String name, int age, int testId) throws CompetitionException {
        System.out.println("PROXY - SAVE PARTICIPANT");
        Participant participant = new Participant(username, name, age);
        participant.setId(testId);
        Request req=new Request.Builder().type(RequestType.SAVE_PARTICIPANT).data(participant).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CompetitionException(err);
        }
    }

    public void saveRelation(int idTest, int idParticipant) throws CompetitionException {
        System.out.println("PROXY - SAVE RELATION");
        Tuple<Integer, Integer> id = new Tuple<>(idTest, idParticipant);
        TestParticipantRelation testParticipantRelation = new TestParticipantRelation(id);
        Request req=new Request.Builder().type(RequestType.SAVE_RELATION).data(testParticipantRelation).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CompetitionException(err);
        }
    }

    public User[] getLoggedUsers(User user) throws CompetitionException {
        System.out.println("PROXY - GET LOGGED USERS");
        Request req=new Request.Builder().type(RequestType.GET_LOGGED_USERS).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CompetitionException(err);
        }
        User[] users=(User[])response.data();
        return users;
    }

    public TestDTO[] findAllTestDTOs() throws CompetitionException {
        System.out.println("PROXY - FIND ALL TEST DTOs");
        Request req=new Request.Builder().type(RequestType.FIND_ALL_TEST_DTO).data(null).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CompetitionException(err);
        }
        TestDTO[] testDTOS=(TestDTO[])response.data();
        return testDTOS;
    }

    public Participant[] findAllParticipantsForTest(Integer id) throws CompetitionException{
        System.out.println("PROXY - FIND ALL PARTICIPANT FOR TEST");
        Test tesDTO = new Test(null, null);
        tesDTO.setId(id);
        Request req=new Request.Builder().type(RequestType.FIND_ALL_PARTICIPANTS_FOR_TEST).data(tesDTO).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CompetitionException(err);
        }
        Participant[] participants=(Participant[])response.data();
        return participants;
    }


    private void closeConnection() {
        System.out.println("PROXY - CLOSE CONNECTION");
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

    private  void sendRequest(Request request) throws CompetitionException {
        System.out.println("PROXY - SEND REQUEST");
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new CompetitionException("Error sending object "+ e);
        }
    }

    private Response readResponse() throws CompetitionException {
        System.out.println("PROXY - READ RESPONSE");
        Response response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PROXY - RESPONSE READ");
        return response;
    }

    private void initializeConnection() throws CompetitionException {
        System.out.println("PROXY - INITIALIZE CONNECTION");
        try{
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        }catch (IOException e){
            System.out.println("ERROR - INITIALIZE CONNECTION");
            e.printStackTrace();
        }
    }

    private void startReader(){
        System.out.println("PROXY - START READER");

        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response){
        System.out.println("PROXY - HANDLE UPDATE");
        if (response.type() == ResponseType.USER_LOGGED_IN){

            User user = (User) response.data();
            System.out.println("User logged in "+ user);
            try {
                client.userLoggedIn(user);
            } catch (CompetitionException e) {
                e.printStackTrace();
            }
        }

        if (response.type()== ResponseType.USER_LOGGED_OUT){
            User user = (User) response.data();
            System.out.println("User logged out " + user);
            try {
                client.userLoggedOut(user);
            } catch (CompetitionException e) {
                e.printStackTrace();
            }
        }

        if(response.type() == ResponseType.NEW_PARTICIPANT){
            System.out.println("new participant ");
            try{
                client.participantSaved();
            }catch (CompetitionException exception){
                exception.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response){
        System.out.println("PROXY - IS UPDATE");
        return response.type()== ResponseType.USER_LOGGED_IN || response.type()== ResponseType.USER_LOGGED_OUT || response.type()== ResponseType.NEW_PARTICIPANT;
    }

    private class ReaderThread implements Runnable{
        public void run() {
            System.out.println("PROXY - READER THREAD");
            while(!finished){
                try {
                    System.out.println("try to read response");
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("IO");
                    System.out.println("Reading error " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Class");
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
