package competition.network.rpcprotocol;

import competition.model.*;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CompetitionClientRpcWorker implements Runnable, ICompetitionObserver {
    private ICompetitionServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public CompetitionClientRpcWorker(ICompetitionServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("WORKER - RUN");
        while(connected){
            try {
                System.out.println("read request");
                Object request=input.readObject();
                System.out.println((Request)request);
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
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
        System.out.println("WORKER - USER LOGGED IN");
        Response resp=new Response.Builder().type(ResponseType.USER_LOGGED_IN).data(user).build();
        System.out.println("Friend logged in " + user);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void userLoggedOut(User user) throws CompetitionException {
        System.out.println("WORKER - USER LOGGED OUT");
        Response resp=new Response.Builder().type(ResponseType.USER_LOGGED_IN).data(user).build();
        System.out.println("Friend logged out " + user);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void participantSaved() throws CompetitionException {
        System.out.println("WORKER - PARTICIPANT SAVED");
        Response resp=new Response.Builder().type(ResponseType.NEW_PARTICIPANT).data(null).build();
        System.out.println("Participant saved");
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new CompetitionException("Sending error: "+e);
        }
    }


    private static final Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        System.out.println("WORKER - HANDLE REQUEST");
        Response response=null;
        if (request.type()== RequestType.LOGIN){
            System.out.println("Login request ..."+request.type());
            User user= (User)request.data();
            try {
                server.login(user, this);
                return okResponse;

            }
            catch (CompetitionException e) {
                connected=false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.LOGOUT){
            System.out.println("Logout request");
            User user= (User)request.data();
            try {
                server.logout(user, this);
                connected=false;
                return okResponse;

            }
            catch (CompetitionException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.GET_LOGGED_USERS){
            System.out.println("Get logged users request");
            User user = (User)request.data();
            try{
                User[] users = server.getLoggedUsers(user);
                return new Response.Builder().type(ResponseType.GET_LOGGED_USERS).data(users).build();

            }
            catch (CompetitionException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.FIND_USER_BY_USERNAME){
            System.out.println("Find by user by username request");
            User userDTO = (User) request.data();
            try{
                User user = server.findUserByUsername(userDTO.getUsername());
                return new Response.Builder().type(ResponseType.FIND_USER_BY_USERNAME).data(user).build();

            }
            catch (CompetitionException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.SAVE_PARTICIPANT){
            System.out.println("Save participant request");
            Participant participant = (Participant) request.data();
            try{
                server.saveParticipant(participant.getUsername(), participant.getName(), participant.getAge(), participant.getId());
                return okResponse;

            }
            catch (CompetitionException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if(request.type() == RequestType.FIND_ALL_TEST_DTO){
            System.out.println("Find all test dto request");
            try{
                TestDTO[] testDTOs = server.findAllTestDTOs();
                return new Response.Builder().type(ResponseType.FIND_ALL_TEST_DTO).data(testDTOs).build();

            }
            catch (CompetitionException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }



        if(request.type() == RequestType.SAVE_RELATION){
            System.out.println("Save relation request");
            try {
                TestParticipantRelation testParticipantRelation = (TestParticipantRelation) request.data();
                server.saveRelation(testParticipantRelation.getId().getLeft(), testParticipantRelation.getId().getRight());
                return okResponse;
            }
            catch (CompetitionException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if(request.type() == RequestType.FIND_PARTICIPANT_BY_USERNAME){
            System.out.println("Find participant by username request");
            Participant participantDTO = (Participant) request.data();
            try {
                Participant participant = server.findParticipantByUsername(participantDTO.getUsername());
                return new Response.Builder().type(ResponseType.FIND_PARTICIPANT_BY_USERNAME).data(participant).build();

            }
            catch (CompetitionException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if(request.type() == RequestType.FIND_ALL_PARTICIPANTS_FOR_TEST){
            System.out.println("Find all test dto request");
            Test testDTO = (Test) request.data();
            try{
                Participant[] participants = server.findAllParticipantsForTest(testDTO.getId());
                return new Response.Builder().type(ResponseType.FIND_ALL_PARTICIPANTS_FOR_TEST).data(participants).build();

            }
            catch (CompetitionException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("WORKER - SEND RESPONSE");
        System.out.println("sending response "+response);
        synchronized (output){
            output.writeObject(response);
            output.flush();
        }
    }
}
