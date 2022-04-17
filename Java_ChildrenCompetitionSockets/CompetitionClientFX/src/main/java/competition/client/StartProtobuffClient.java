package competition.client;

import competition.client.gui.CompetitionController;
import competition.network.protobuffprotocol.ProtoCompetitionProxy;
import competition.network.rpcprotocol.CompetitionServicesRpcProxy;
import competition.services.ICompetitionServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class StartProtobuffClient extends Application {
    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {

        Properties clientProperties = new Properties();
        try {
            System.out.println("Try set Client properties. ");
            clientProperties.load(StartRpcClientFX.class.getResourceAsStream("/competitionclient.properties"));
            System.out.println("Client properties set. ");
            clientProperties.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find competitionclient.properties " + e);
            return;
        }

        String serverIP = clientProperties.getProperty("competition.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProperties.getProperty("competition.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        ICompetitionServices server = new ProtoCompetitionProxy(serverIP, serverPort);


        primaryStage.setTitle("Competition");
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("views/competitionPage.fxml"));
        Parent pane = loader.load();

        CompetitionController competitionController = loader.getController();
        competitionController.setServer(server);

        loader.setController(competitionController);

        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
