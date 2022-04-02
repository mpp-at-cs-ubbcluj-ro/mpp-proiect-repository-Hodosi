package competition.network.rpcprotocol;

import competition.model.User;
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
        Response resp=new Response.Builder().type(ResponseType.NEW_PARTICIPANT).data(null).build();
        System.out.println("Participant saved");
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new CompetitionException("Sending error: "+e);
        }
    }


    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response=null;
        if (request.type()== RequestType.LOGIN){
            System.out.println("Login request ..."+request.type());
            User user= (User)request.data();
            try {
                server.login(user, this);
                return okResponse;
            } catch (CompetitionException e) {
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

            } catch (CompetitionException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }
}
