package competition.client.gui;

import competition.model.Participant;
import competition.model.TestDTO;
import competition.model.User;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class CompetitionController implements Initializable, ICompetitionObserver {
//    private IUserService userService;
//    private ITestService testService;
//    private IParticipantService participantService;
//    private ITestParticipantRelationService testParticipantRelationService;

    private ICompetitionServices server;
    private User crtUser;

    public void setServer(ICompetitionServices s){
        this.server = s;
    }

//    public void setUserService(IUserService userService){
//        this.userService = userService;
//    }
//
//    public void setTestService(ITestService testService){
//        this.testService = testService;
//    }
//
//    public void setParticipantService(IParticipantService participantService){
//        this.participantService = participantService;
//    }
//
//    public void setTestParticipantRelationService(ITestParticipantRelationService testParticipantRelationService){
//        this.testParticipantRelationService = testParticipantRelationService;
//    }

    ObservableList<TestDTO> testObservableList = FXCollections.observableArrayList();
    ObservableList<Participant> participantObservableList = FXCollections.observableArrayList();
    ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TextField textFieldUsernameLogin;

    @FXML
    private PasswordField passwordFieldLogin;

    @FXML
    private Label errorLoginLabel;

    @FXML
    TableView<TestDTO> tableViewTests;

    @FXML
    TableColumn<TestDTO, String> tableColumnType;

    @FXML
    TableColumn<TestDTO, String> tableColumnAgeCategory;

    @FXML
    TableColumn<TestDTO, Integer> tableColumnNoCompetitors;

    @FXML
    TableView<Participant> tableViewParticipant;

    @FXML
    TableColumn<Participant, String> participantsTableColumnUsername;

    @FXML
    TableColumn<Participant, String> participantsTableColumnName;

    @FXML
    TableColumn<Participant, Integer> participantsTableColumnAge;

    @FXML
    TextField textFieldNameSignUp;

    @FXML
    TextField textFieldUsernameSignUp;

    @FXML
    ComboBox<Integer> comboBoxAgeSignUp;

    @FXML
    Button buttonTestSignUp;

    @FXML
    Label labelProgramError;

    @FXML
    void loginAction(){
        String username = textFieldUsernameLogin.getText().trim();
        String password = passwordFieldLogin.getText().trim();
        crtUser = new User("nullText", "nullText", username, password);

        try{
            server.login(crtUser, this);
            System.out.println("Start init main page");
            initMainPage();
            this.crtUser = server.findUserByUsername(username);

        } catch (CompetitionException exception){
//            errorLoginLabel.setText(exception.getMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        }
    }

    @FXML
    void logoutAction()  {
        try{
            server.logout(crtUser, this);
        }
        catch (CompetitionException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("logout exception");
            alert.showAndWait();
        }
        loginAnchorPane.setVisible(true);
        mainAnchorPane.setVisible(false);
    }

    private void initMainPage(){
        loginAnchorPane.setVisible(false);
        mainAnchorPane.setVisible(true);
        initViewTests();
        initViewParticipants();
        updateComboBoxAge("6");
    }

    private void initViewTests(){
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("testType"));
        tableColumnAgeCategory.setCellValueFactory(new PropertyValueFactory<>("testAgeCategory"));
        tableColumnNoCompetitors.setCellValueFactory(new PropertyValueFactory<>("noCompetitors"));
        tableViewTests.setItems(testObservableList);
        try {
            testObservableList.setAll(server.findAllTestDTOs());
        }catch (CompetitionException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("init view tests exception");
            alert.showAndWait();
        }

        tableViewTests.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                try {
                    List<Participant> participantList = List.of(server.findAllParticipantsForTest(newValue.getId()));
                    participantObservableList.setAll(participantList);
                    updateComboBoxAge(newValue.getTestAgeCategory());
                }
                catch (CompetitionException exception){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("MPP chat");
                    alert.setHeaderText("Authentication failure");
                    alert.setContentText("init view tests exception");
                    alert.showAndWait();
                }

            }
        });
    }

    private void initViewParticipants(){
        participantsTableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        participantsTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        participantsTableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableViewParticipant.setItems(participantObservableList);
    }

    private void updateComboBoxAge(String ageCategory){
        List<Integer> ages;
        if (ageCategory.contains("6")){
            ages = IntStream.range(6,9).boxed().collect(Collectors.toList());
        }else if(ageCategory.contains("9")){
            ages = IntStream.range(9,12).boxed().collect(Collectors.toList());
        }else{
            ages = IntStream.range(12,16).boxed().collect(Collectors.toList());
        }
        comboBoxAgeSignUp.getItems().setAll(ages);
        comboBoxAgeSignUp.getSelectionModel().selectFirst();
    }

    @FXML
    void signUpForTest(){
        String username = textFieldUsernameSignUp.getText().trim();
        String name = textFieldNameSignUp.getText().trim();
        int age = comboBoxAgeSignUp.getValue();
        TestDTO testDTO = tableViewTests.getSelectionModel().getSelectedItem();

        if(username.equals("")){
            labelProgramError.setText("Last error: Username can't be empty");
            return;
        }

        if(name.equals("")){
            labelProgramError.setText("Last error: Name can't be empty");
            return;
        }

        if(testDTO == null){
            labelProgramError.setText("Last error: No test selected");
            return;
        }

        try{
            server.saveParticipant(username, name, age, testDTO.getId());
        }
        catch (CompetitionException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("sign up save exception");
            alert.showAndWait();
            return;
        }
        Participant participant = null;
        try {
            participant = server.findParticipantByUsername(username);
        }catch (CompetitionException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("sign up participant exception");
            alert.showAndWait();
            return;
        }

        try {
            server.saveRelation(testDTO.getId(), participant.getId());
        }catch (CompetitionException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("sign up save relation exception");
            alert.showAndWait();
            return;
        }

        try {
            testObservableList.clear();
            testObservableList.setAll(List.of(server.findAllTestDTOs()));
        }catch (CompetitionException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("sign up find test dtos exception");
            alert.showAndWait();
            return;
        }

        try {
            List<Participant> participantList = List.of(server.findAllParticipantsForTest(testDTO.getId()));
            participantObservableList.setAll(participantList);
        }catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("sign up find test participant list exception");
            alert.showAndWait();

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("INIT : am in lista useri: " + users.size());

        System.out.println("END INIT!!!!!!!!!");
    }

    @Override
    public void userLoggedIn(User user) throws CompetitionException {
        Platform.runLater(()->{
            System.out.println("Friends logged in " + user.getUsername());
        });
    }

    @Override
    public void userLoggedOut(User user) throws CompetitionException {
        Platform.runLater(()->{
            System.out.println("Friends logged out " + user.getUsername());
        });

    }

    @Override
    public void participantSaved() throws CompetitionException {
        Platform.runLater(()->{
            try{
                testObservableList.clear();
                testObservableList.setAll(List.of(server.findAllTestDTOs()));
            }catch (CompetitionException exception){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("MPP chat");
                alert.setHeaderText("Authentication failure");
                alert.setContentText("sign up find test dtos exception");
                alert.showAndWait();
                return;
            }

            try{
                TestDTO testDTO = tableViewTests.getSelectionModel().getSelectedItem();
                List<Participant> participantList = List.of(server.findAllParticipantsForTest(testDTO.getId()));
                participantObservableList.setAll(participantList);
            }catch (Exception e){
                List<Participant> participantList = new ArrayList<>();
                participantObservableList.setAll(participantList);
            }
        });
    }
}
