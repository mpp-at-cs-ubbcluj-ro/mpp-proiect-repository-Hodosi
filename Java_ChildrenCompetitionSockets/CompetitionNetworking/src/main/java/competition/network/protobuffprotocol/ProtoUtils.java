package competition.network.protobuffprotocol;

import competition.model.*;

public class ProtoUtils {

    // requests

    public static CompetitionProtobufs.CompetitionRequest createLoginRequest(User user){
        CompetitionProtobufs.User userDTO=CompetitionProtobufs.User.newBuilder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .build();

        CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.Login)
                .setUser(userDTO)
                .build();

        return request;
    }

    public static CompetitionProtobufs.CompetitionRequest createLogoutRequest(User user){
        CompetitionProtobufs.User userDTO=CompetitionProtobufs.User.newBuilder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .build();

        CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.Logout)
                .setUser(userDTO)
                .build();

        return request;
    }

    public static CompetitionProtobufs.CompetitionRequest createFindUserByUsernameRequest(User user){
        CompetitionProtobufs.User userDTO=CompetitionProtobufs.User.newBuilder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .build();

        CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.FindUserByUsername)
                .setUser(userDTO)
                .build();

        return request;
    }

    public static CompetitionProtobufs.CompetitionRequest createFindParticipantByUsernameRequest(Participant participant){
        CompetitionProtobufs.Participant participantDTO=CompetitionProtobufs.Participant.newBuilder()
                .setUsername(participant.getUsername())
                .setName(participant.getName())
                .setAge(String.valueOf(participant.getAge()))
                .setTestId(String.valueOf(participant.getId()))
                .build();

        CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.FindParticipantByUsername)
                .setParticipant(participantDTO)
                .build();

        return request;
    }

    public static CompetitionProtobufs.CompetitionRequest createSaveParticipantRequest(Participant participant){
        CompetitionProtobufs.Participant participantDTO=CompetitionProtobufs.Participant.newBuilder()
                .setUsername(participant.getUsername())
                .setName(participant.getName())
                .setAge(String.valueOf(participant.getAge()))
                .setTestId(String.valueOf(participant.getId()))
                .build();

        CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.SaveParticipant)
                .setParticipant(participantDTO)
                .build();

        return request;
    }

    public static CompetitionProtobufs.CompetitionRequest createSaveRelationRequest(TestParticipantRelation relation){
        CompetitionProtobufs.TestParticipantRelation testParticipantRelationDTO=CompetitionProtobufs.TestParticipantRelation.newBuilder()
                .setTestId(String.valueOf(relation.getId().getLeft()))
                .setParticipantId(String.valueOf(relation.getId().getRight()))
                .build();

        CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.SaveRelation)
                .setTestParticipantRelation(testParticipantRelationDTO)
                .build();

        return request;
    }

    public static CompetitionProtobufs.CompetitionRequest createFindAllTestDTOsRequest(){
        CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.FindAllTestDto)
                .build();

        return request;
    }

    public static CompetitionProtobufs.CompetitionRequest createFindAllParticipantsForTestRequest(Test test){
        CompetitionProtobufs.Test testDTO=CompetitionProtobufs.Test.newBuilder()
                .setTestId(String.valueOf(test.getId()))
                .build();

        CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.FinAllParticipantsForTest)
                .setTest(testDTO)
                .build();

        return request;
    }

    // responses

    public static CompetitionProtobufs.CompetitionResponse createOkResponse(){
        CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.newBuilder()
                .setType(CompetitionProtobufs.CompetitionResponse.Type.Ok)
                .build();

        return response;
    }

    public static CompetitionProtobufs.CompetitionResponse createErrorResponse(String text){
        CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.newBuilder()
                .setType(CompetitionProtobufs.CompetitionResponse.Type.Error)
                .setError(text)
                .build();

        return response;
    }

    public static CompetitionProtobufs.CompetitionResponse createNewParticipantResponse() {
        CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.newBuilder()
                .setType(CompetitionProtobufs.CompetitionResponse.Type.NewParticipant)
                .build();

        return response;
    }

    public static CompetitionProtobufs.CompetitionResponse createFindByUsernameResponse(User user){
        CompetitionProtobufs.User userDTO=CompetitionProtobufs.User.newBuilder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .build();

        CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.newBuilder()
                .setType(CompetitionProtobufs.CompetitionResponse.Type.FindUserByUsername)
                .setUser(userDTO)
                .build();

        return response;
    }

    public static CompetitionProtobufs.CompetitionResponse createFindParticipantByUsernameResponse(Participant participant){
        CompetitionProtobufs.Participant participantDTO=CompetitionProtobufs.Participant.newBuilder()
                .setUsername(participant.getUsername())
                .setName(participant.getName())
                .setAge(String.valueOf(participant.getAge()))
                .setTestId(String.valueOf(participant.getId()))
                .build();

        CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.newBuilder()
                .setType(CompetitionProtobufs.CompetitionResponse.Type.FindParticipantByUsername)
                .setParticipant(participantDTO)
                .build();

        return response;
    }

    public static CompetitionProtobufs.CompetitionResponse createFindAllTestDTOsResponse(TestDTO[] testDTOs){
        CompetitionProtobufs.CompetitionResponse.Builder response=CompetitionProtobufs.CompetitionResponse.newBuilder()
                .setType(CompetitionProtobufs.CompetitionResponse.Type.FindAllTestDto);
        for (TestDTO test: testDTOs){
            CompetitionProtobufs.TestDTO testDTO=CompetitionProtobufs.TestDTO.newBuilder()
                    .setTestType(test.getTestType())
                    .setTestAgeCategory(test.getTestAgeCategory())
                    .setNoCompetitors(String.valueOf(test.getNoCompetitors()))
                    .setTestId(String.valueOf(test.getId()))
                    .build();
            response.addTestDTOs(testDTO);
        }

        return response.build();
    }

    public static CompetitionProtobufs.CompetitionResponse createFindAllParticipantForTestResponse(Participant[] participants){
        CompetitionProtobufs.CompetitionResponse.Builder response=CompetitionProtobufs.CompetitionResponse.newBuilder()
                .setType(CompetitionProtobufs.CompetitionResponse.Type.FindAllParticipantsForTest);
        for (Participant participant: participants){
            CompetitionProtobufs.Participant participantDTO=CompetitionProtobufs.Participant.newBuilder()
                    .setUsername(participant.getUsername())
                    .setName(participant.getName())
                    .setAge(String.valueOf(participant.getAge()))
                    .setTestId(String.valueOf(participant.getId()))
                    .build();

            response.addParticipants(participantDTO);
        }

        return response.build();
    }


    // utils response

    public static String getError(CompetitionProtobufs.CompetitionResponse response){
        String errorMessage=response.getError();
        return errorMessage;
    }

    public static User getUser(CompetitionProtobufs.CompetitionResponse response){
        CompetitionProtobufs.User userDTO = response.getUser();
        User user=new User(userDTO.getFirstname(), userDTO.getLastname(), userDTO.getUsername(), userDTO.getPassword());
        return user;
    }

    public static Participant getParticipant(CompetitionProtobufs.CompetitionResponse response){
        CompetitionProtobufs.Participant participantDTO = response.getParticipant();
//        User user=new User(userDTO.getFirstname(), userDTO.getLastname(), userDTO.getUsername(), userDTO.getPassword());
        Participant participant = new Participant(participantDTO.getUsername(), participantDTO.getName(), Integer.valueOf(participantDTO.getAge()));
        participant.setId(Integer.valueOf(participantDTO.getTestId()));
        return participant;
    }


    public static TestDTO[] getTestDTOs(CompetitionProtobufs.CompetitionResponse response){
        TestDTO[] testDTOs = new TestDTO[response.getTestDTOsCount()];
        for(int i = 0; i < response.getTestDTOsCount(); i++){
            CompetitionProtobufs.TestDTO testDTO = response.getTestDTOs(i);
            TestDTO test = new TestDTO(testDTO.getTestType(), testDTO.getTestAgeCategory(), Integer.valueOf(testDTO.getNoCompetitors()));
            test.setId(Integer.valueOf(testDTO.getTestId()));
            testDTOs[i] = test;
        }
        return testDTOs;
    }

    public static Participant[] getParticipants(CompetitionProtobufs.CompetitionResponse response){
        Participant[] participants = new Participant[response.getParticipantsCount()];
        for(int i = 0; i < response.getParticipantsCount(); i++){
            CompetitionProtobufs.Participant participantDTO = response.getParticipants(i);
            Participant participant = new Participant(participantDTO.getUsername(), participantDTO.getName(), Integer.valueOf(participantDTO.getAge()));
            participant.setId(Integer.valueOf(participantDTO.getTestId()));
            participants[i] = participant;
        }
        return participants;
    }

    // utils request

    public static User getUser(CompetitionProtobufs.CompetitionRequest request){
        CompetitionProtobufs.User userDTO = request.getUser();
        User user=new User(userDTO.getFirstname(), userDTO.getLastname(), userDTO.getUsername(), userDTO.getPassword());
        return user;
    }


    public static Participant getParticipant(CompetitionProtobufs.CompetitionRequest request){
        CompetitionProtobufs.Participant participantDTO = request.getParticipant();
//        User user=new User(userDTO.getFirstname(), userDTO.getLastname(), userDTO.getUsername(), userDTO.getPassword());
        Participant participant = new Participant(participantDTO.getUsername(), participantDTO.getName(), Integer.valueOf(participantDTO.getAge()));
        participant.setId(Integer.valueOf(participantDTO.getTestId()));
        return participant;
    }


    public static TestParticipantRelation getTestParticipantRelation(CompetitionProtobufs.CompetitionRequest request){
        CompetitionProtobufs.TestParticipantRelation testParticipantRelationDTO = request.getTestParticipantRelation();
        Tuple<Integer, Integer> id = new Tuple<>(Integer.valueOf(testParticipantRelationDTO.getTestId()), Integer.valueOf(testParticipantRelationDTO.getParticipantId()));
        TestParticipantRelation testParticipantRelation = new TestParticipantRelation(id);
        return testParticipantRelation;
    }

    public static Test getTest(CompetitionProtobufs.CompetitionRequest request){
        CompetitionProtobufs.Test testDTO = request.getTest();
        Test test = new Test(null, null);
        test.setId(Integer.valueOf(testDTO.getTestId()));
        return test;
    }


}
