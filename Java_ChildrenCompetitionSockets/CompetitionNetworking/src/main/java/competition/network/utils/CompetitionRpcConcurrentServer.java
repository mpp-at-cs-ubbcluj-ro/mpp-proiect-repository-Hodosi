package competition.network.utils;

import competition.network.rpcprotocol.CompetitionClientRpcWorker;
import competition.services.ICompetitionServices;

import java.net.Socket;

public class CompetitionRpcConcurrentServer extends AbstractConcurrentServer{
    private ICompetitionServices competitionServer;
    public CompetitionRpcConcurrentServer(int port, ICompetitionServices competitionServer) {
        super(port);
        this.competitionServer = competitionServer;
        System.out.println("Competition - CompetitionRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        CompetitionClientRpcWorker worker=new CompetitionClientRpcWorker(competitionServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
