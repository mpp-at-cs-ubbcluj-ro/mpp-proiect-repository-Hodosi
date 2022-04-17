package competition.network.utils;

import competition.network.protobuffprotocol.ProtoCompetitionWorker;
import competition.services.ICompetitionServices;

import java.net.Socket;

public class CompetitionProtobuffConcurrentServer extends AbstractConcurrentServer {
    private ICompetitionServices competitionServer;
    public CompetitionProtobuffConcurrentServer(int port, ICompetitionServices competitionServer) {
        super(port);
        this.competitionServer = competitionServer;
        System.out.println("Chat- ChatProtobuffConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ProtoCompetitionWorker worker=new ProtoCompetitionWorker(competitionServer,client);
        Thread tw=new Thread(worker);
        return tw;
    }
}
