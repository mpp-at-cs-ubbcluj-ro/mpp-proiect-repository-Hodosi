import controller.CompetitionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import repository.*;
import service.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        Properties properties = new Properties();
        try{
            properties.load(new FileReader("db.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        IUserRepository userRepository = new UserDbRepository(properties);
        IUserService userService = new UserService(userRepository);

        ITestRepository testRepository = new TestDbRepository(properties);
        IParticipantRepository participantRepository = new ParticipantDbRepository(properties);

        IParticipantService participantService = new ParticipantService(participantRepository, testRepository);
        ITestService testService = new TestService(testRepository, participantRepository);

        ITestParticipantRelationRepository testParticipantRelationRepository = new TestParticipantRelationDbRepository(properties);
        ITestParticipantRelationService testParticipantRelationService = new TestParticipantRelationService(testParticipantRelationRepository);

        primaryStage.setTitle("Competition");
        FXMLLoader loader = new FXMLLoader();
        Pane pane = loader.load(
                getClass().getResourceAsStream("views/competitionPage.fxml")
        );

        CompetitionController competitionController = loader.getController();

        competitionController.setUserService(userService);
        competitionController.setTestService(testService);
        competitionController.setParticipantService(participantService);
        competitionController.setTestParticipantRelationService(testParticipantRelationService);

        loader.setController(competitionController);

        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
