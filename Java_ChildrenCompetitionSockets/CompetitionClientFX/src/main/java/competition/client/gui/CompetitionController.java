package competition.client.gui;

import exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.*;
import service.IParticipantService;
import service.ITestParticipantRelationService;
import service.ITestService;
import service.IUserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class CompetitionController {
    private IUserService userService;
    private ITestService testService;
    private IParticipantService participantService;
    private ITestParticipantRelationService testParticipantRelationService;


    public void setUserService(IUserService userService){
        this.userService = userService;
    }

    public void setTestService(ITestService testService){
        this.testService = testService;
    }

    public void setParticipantService(IParticipantService participantService){
        this.participantService = participantService;
    }

    public void setTestParticipantRelationService(ITestParticipantRelationService testParticipantRelationService){
        this.testParticipantRelationService = testParticipantRelationService;
    }

    ObservableList<TestDTO> testObservableList = FXCollections.observableArrayList();
    ObservableList<Participant> participantObservableList = FXCollections.observableArrayList();

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

        try{
            userService.login(username, password);
            initMainPage();

        } catch (WrongUsernameException | WrongPasswordException exception){
            errorLoginLabel.setText(exception.getMessage());
        }
    }

    @FXML
    void logoutAction(){
        userService.logout();
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
        testObservableList.setAll(testService.findAllTestDTOs());

        tableViewTests.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                List<Participant> participantList = participantService.findAllParticipantsForTest(newValue.getId());
                participantObservableList.setAll(participantList);
                updateComboBoxAge(newValue.getTestAgeCategory());
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
            participantService.save(username, name, age, testDTO.getId());
        }
        catch (TestJoinedException | TestLimitException | InvalidUsernameException exception){
            labelProgramError.setText("Last error: " + exception.getMessage());
            return;
        }

        Participant participant = participantService.findByUsername(username);

        testParticipantRelationService.save(testDTO.getId(), participant.getId());

        testObservableList.clear();
        testObservableList.setAll(testService.findAllTestDTOs());

        List<Participant> participantList = participantService.findAllParticipantsForTest(testDTO.getId());
        participantObservableList.setAll(participantList);
    }
}
