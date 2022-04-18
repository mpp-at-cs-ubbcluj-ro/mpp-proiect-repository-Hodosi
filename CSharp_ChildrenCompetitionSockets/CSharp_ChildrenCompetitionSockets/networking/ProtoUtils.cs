using System;
using Chat.Protocol;

using modelOriginal=CSharp_ChildrenCompetitionGUI.model;
using proto=Chat.Protocol;

namespace networking
{
    public class ProtoUtils
    {
           public static CompetitionRequest createLoginRequest(modelOriginal.User user){
        // CompetitionProtobufs.User userDTO=CompetitionProtobufs.User.newBuilder()
        //         .setFirstname(user.getFirstname())
        //         .setLastname(user.getLastname())
        //         .setUsername(user.getUsername())
        //         .setPassword(user.getPassword())
        //         .build();

        proto.User userDTO = new proto.User
            {Firstname = user.firstname, Lastname = user.lastname, Username = user.username, Password = user.password};

        // CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionRequest.Type.Login)
        //         .setUser(userDTO)
        //         .build();

        CompetitionRequest request = new CompetitionRequest {Type = CompetitionRequest.Types.Type.Login, User = userDTO};

        return request;
    }

    public static CompetitionRequest createLogoutRequest(modelOriginal.User user){
        // CompetitionProtobufs.User userDTO=CompetitionProtobufs.User.newBuilder()
        //         .setFirstname(user.getFirstname())
        //         .setLastname(user.getLastname())
        //         .setUsername(user.getUsername())
        //         .setPassword(user.getPassword())
        //         .build();

        proto.User userDTO = new proto.User
            {Firstname = user.firstname, Lastname = user.lastname, Username = user.username, Password = user.password};
        
        // CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionRequest.Type.Logout)
        //         .setUser(userDTO)
        //         .build();

        CompetitionRequest request = new CompetitionRequest {Type = CompetitionRequest.Types.Type.Logout, User = userDTO};
        
        return request;
    }

    public static CompetitionRequest createFindUserByUsernameRequest(modelOriginal.User user){
        // CompetitionProtobufs.User userDTO=CompetitionProtobufs.User.newBuilder()
        //         .setFirstname(user.getFirstname())
        //         .setLastname(user.getLastname())
        //         .setUsername(user.getUsername())
        //         .setPassword(user.getPassword())
        //         .build();
        
        proto.User userDTO = new proto.User
            {
                Firstname = user.firstname,
                Lastname = user.lastname, 
                Username = user.username,
                Password = user.password
            };

        // CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionRequest.Type.FindUserByUsername)
        //         .setUser(userDTO)
        //         .build();
        
        CompetitionRequest request = new CompetitionRequest {Type = CompetitionRequest.Types.Type.FindUserByUsername, User = userDTO};

        return request;
    }

    public static CompetitionRequest createFindParticipantByUsernameRequest(modelOriginal.Participant participant){
        // CompetitionProtobufs.Participant participantDTO=CompetitionProtobufs.Participant.newBuilder()
        //         .setUsername(participant.getUsername())
        //         .setName(participant.getName())
        //         .setAge(String.valueOf(participant.getAge()))
        //         .setTestId(String.valueOf(participant.getId()))
        //         .build();

        proto.Participant participantDTO = new proto.Participant
        {
            Username = participant.username,
            Name = participant.name,
            Age = participant.age.ToString(),
            TestId = participant.id.ToString()
        };

        // CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionRequest.Type.FindParticipantByUsername)
        //         .setParticipant(participantDTO)
        //         .build();

        CompetitionRequest request = new CompetitionRequest {Type = CompetitionRequest.Types.Type.FindParticipantByUsername, Participant = participantDTO};

        return request;
    }

    public static CompetitionRequest createSaveParticipantRequest(modelOriginal.Participant participant){
        // CompetitionProtobufs.Participant participantDTO=CompetitionProtobufs.Participant.newBuilder()
        //         .setUsername(participant.getUsername())
        //         .setName(participant.getName())
        //         .setAge(String.valueOf(participant.getAge()))
        //         .setTestId(String.valueOf(participant.getId()))
        //         .build();
        
        proto.Participant participantDTO = new proto.Participant
        {
            Username = participant.username,
            Name = participant.name,
            Age = participant.age.ToString(),
            TestId = participant.id.ToString()
        };

        // CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionRequest.Type.SaveParticipant)
        //         .setParticipant(participantDTO)
        //         .build();

        CompetitionRequest request = new CompetitionRequest {Type = CompetitionRequest.Types.Type.SaveParticipant, Participant = participantDTO};

        return request;
    }

    public static CompetitionRequest createSaveRelationRequest(modelOriginal.TestParticipantRelation relation){
        // CompetitionProtobufs.TestParticipantRelation testParticipantRelationDTO=CompetitionProtobufs.TestParticipantRelation.newBuilder()
        //         .setTestId(String.valueOf(relation.getId().getLeft()))
        //         .setParticipantId(String.valueOf(relation.getId().getRight()))
        //         .build();

        proto.TestParticipantRelation testParticipantRelationDTO = new proto.TestParticipantRelation
        {
            TestId = relation.id.Item1.ToString(),
            ParticipantId = relation.id.Item2.ToString()
        };

        // CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionRequest.Type.SaveRelation)
        //         .setTestParticipantRelation(testParticipantRelationDTO)
        //         .build();

        CompetitionRequest request = new CompetitionRequest {Type = CompetitionRequest.Types.Type.SaveRelation, TestParticipantRelation = testParticipantRelationDTO};
        
        return request;
    }

    public static CompetitionRequest createFindAllTestDTOsRequest(){
        // CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionRequest.Type.FindAllTestDto)
        //         .build();

        CompetitionRequest request = new CompetitionRequest {Type = CompetitionRequest.Types.Type.FindAllTestDto};

        return request;
    }

    public static CompetitionRequest createFindAllParticipantsForTestRequest(modelOriginal.Test test){
        // CompetitionProtobufs.Test testDTO=CompetitionProtobufs.Test.newBuilder()
        //         .setTestId(String.valueOf(test.getId()))
        //         .build();

        proto.Test testDTO = new proto.Test
        {
            TestId = test.id.ToString()
        };
            
        // CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionRequest.Type.FinAllParticipantsForTest)
        //         .setTest(testDTO)
        //         .build();
        
        CompetitionRequest request = new CompetitionRequest {Type = CompetitionRequest.Types.Type.FinAllParticipantsForTest, Test = testDTO};

        return request;
    }

    // responses

    public static CompetitionResponse createOkResponse(){
        // CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionResponse.Type.Ok)
        //         .build();

        CompetitionResponse response = new CompetitionResponse { Type = CompetitionResponse.Types.Type.Ok};

        return response;
    }

    public static CompetitionResponse createErrorResponse(String text){
        // CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionResponse.Type.Error)
        //         .setError(text)
        //         .build();
        
        CompetitionResponse response = new CompetitionResponse { Type = CompetitionResponse.Types.Type.Error, Error = text};

        return response;
    }

    public static CompetitionResponse createNewParticipantResponse() {
        // CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionResponse.Type.NewParticipant)
        //         .build();
        
        CompetitionResponse response = new CompetitionResponse { Type = CompetitionResponse.Types.Type.NewParticipant};
        
        return response;
    }

    public static CompetitionResponse createFindByUsernameResponse(modelOriginal.User user){
        // CompetitionProtobufs.User userDTO=CompetitionProtobufs.User.newBuilder()
        //         .setFirstname(user.getFirstname())
        //         .setLastname(user.getLastname())
        //         .setUsername(user.getUsername())
        //         .setPassword(user.getPassword())
        //         .build();
        
        proto.User userDTO = new proto.User
        {
            Firstname = user.firstname,
            Lastname = user.lastname, 
            Username = user.username,
            Password = user.password
        };

        // CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionResponse.Type.FindUserByUsername)
        //         .setUser(userDTO)
        //         .build();

        CompetitionResponse response = new CompetitionResponse { Type = CompetitionResponse.Types.Type.FindUserByUsername, User = userDTO};
        
        return response;
    }

    public static CompetitionResponse createFindParticipantByUsernameResponse(modelOriginal.Participant participant){
        // CompetitionProtobufs.Participant participantDTO=CompetitionProtobufs.Participant.newBuilder()
        //         .setUsername(participant.getUsername())
        //         .setName(participant.getName())
        //         .setAge(String.valueOf(participant.getAge()))
        //         .setTestId(String.valueOf(participant.getId()))
        //         .build();
        
        proto.Participant participantDTO = new proto.Participant
        {
            Username = participant.username,
            Name = participant.name,
            Age = participant.age.ToString(),
            TestId = participant.id.ToString()
        };

        // CompetitionProtobufs.CompetitionResponse response=CompetitionProtobufs.CompetitionResponse.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionResponse.Type.FindParticipantByUsername)
        //         .setParticipant(participantDTO)
        //         .build();

        CompetitionResponse response = new CompetitionResponse { Type = CompetitionResponse.Types.Type.FindParticipantByUsername, Participant = participantDTO};

        return response;
    }

    public static CompetitionResponse createFindAllTestDTOsResponse(modelOriginal.TestDTO[] testDTOs){
        // CompetitionProtobufs.CompetitionResponse.Builder response=CompetitionProtobufs.CompetitionResponse.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionResponse.Type.FindAllTestDto);
        
        CompetitionResponse response = new CompetitionResponse { Type = CompetitionResponse.Types.Type.FindAllTestDto};

        // for (TestDTO test: testDTOs){
        //     CompetitionProtobufs.TestDTO testDTO=CompetitionProtobufs.TestDTO.newBuilder()
        //             .setTestType(test.getTestType())
        //             .setTestAgeCategory(test.getTestAgeCategory())
        //             .setNoCompetitors(String.valueOf(test.getNoCompetitors()))
        //             .setTestId(String.valueOf(test.getId()))
        //             .build();
        //     response.addTestDTOs(testDTO);
        // }
        //
        // return response.build();

        foreach (modelOriginal.TestDTO test in testDTOs)
        {
            proto.TestDTO testDTO = new proto.TestDTO
            {
                TestType = test.testType,
                TestAgeCategory = test.testAgeCategory,
                NoCompetitors = test.noCompetitors.ToString(),
                TestId = test.id.ToString()
            };
            
            response.TestDTOs.Add(testDTO);
        }

        return response;
    }

    public static CompetitionResponse createFindAllParticipantForTestResponse(modelOriginal.Participant[] participants){
        // CompetitionProtobufs.CompetitionResponse.Builder response=CompetitionProtobufs.CompetitionResponse.newBuilder()
        //         .setType(CompetitionProtobufs.CompetitionResponse.Type.FindAllParticipantsForTest);
        
        CompetitionResponse response = new CompetitionResponse { Type = CompetitionResponse.Types.Type.FindAllParticipantsForTest};

        // for (Participant participant: participants){
        //     CompetitionProtobufs.Participant participantDTO=CompetitionProtobufs.Participant.newBuilder()
        //             .setUsername(participant.getUsername())
        //             .setName(participant.getName())
        //             .setAge(String.valueOf(participant.getAge()))
        //             .setTestId(String.valueOf(participant.getId()))
        //             .build();
        //
        //     response.addParticipants(participantDTO);
        // }
        //
        // return response.build();
        
        foreach (modelOriginal.Participant participant in participants)
        {
            proto.Participant participantDTO = new proto.Participant
            {
                Username = participant.username,
                Name = participant.name,
                Age = participant.age.ToString(),
                TestId = participant.id.ToString()
            };
            
            response.Participants.Add(participantDTO);
        }

        return response;
    }


    // utils response

    public static String getError(CompetitionResponse response){
        String errorMessage=response.Error;
        return errorMessage;
    }

    public static modelOriginal.User getUser(CompetitionResponse response){
        // CompetitionProtobufs.User userDTO = response.getUser();
        // User user=new User(userDTO.getFirstname(), userDTO.getLastname(), userDTO.getUsername(), userDTO.getPassword());

        modelOriginal.User user = new modelOriginal.User(response.User.Firstname, response.User.Lastname, response.User.Username,
            response.User.Password);

        return user;
    }

//     public static Participant getParticipant(CompetitionProtobufs.CompetitionResponse response){
//         CompetitionProtobufs.Participant participantDTO = response.getParticipant();
// //        User user=new User(userDTO.getFirstname(), userDTO.getLastname(), userDTO.getUsername(), userDTO.getPassword());
//         Participant participant = new Participant(participantDTO.getUsername(), participantDTO.getName(), Integer.valueOf(participantDTO.getAge()));
//         participant.setId(Integer.valueOf(participantDTO.getTestId()));
//         return participant;
//     }
//
//
//     public static TestDTO[] getTestDTOs(CompetitionProtobufs.CompetitionResponse response){
//         TestDTO[] testDTOs = new TestDTO[response.getTestDTOsCount()];
//         for(int i = 0; i < response.getTestDTOsCount(); i++){
//             CompetitionProtobufs.TestDTO testDTO = response.getTestDTOs(i);
//             TestDTO test = new TestDTO(testDTO.getTestType(), testDTO.getTestAgeCategory(), Integer.valueOf(testDTO.getNoCompetitors()));
//             test.setId(Integer.valueOf(testDTO.getTestId()));
//             testDTOs[i] = test;
//         }
//         return testDTOs;
//     }
//     
//     public static Participant[] getParticipants(CompetitionProtobufs.CompetitionResponse response){
//         Participant[] participants = new Participant[response.getParticipantsCount()];
//         for(int i = 0; i < response.getParticipantsCount(); i++){
//             CompetitionProtobufs.Participant participantDTO = response.getParticipants(i);
//             Participant participant = new Participant(participantDTO.getUsername(), participantDTO.getName(), Integer.valueOf(participantDTO.getAge()));
//             participant.setId(Integer.valueOf(participantDTO.getTestId()));
//             participants[i] = participant;
//         }
//         return participants;
//     }

    // utils request

    public static modelOriginal.User getUser(CompetitionRequest request){
        // CompetitionProtobufs.User userDTO = request.getUser();
        // User user=new User(userDTO.getFirstname(), userDTO.getLastname(), userDTO.getUsername(), userDTO.getPassword());
        //
        modelOriginal.User user = new modelOriginal.User(request.User.Firstname, request.User.Lastname, request.User.Username,
            request.User.Password);
        return user;
    }


    public static modelOriginal.Participant getParticipant(CompetitionRequest request){
//         CompetitionProtobufs.Participant participantDTO = request.getParticipant();
// //        User user=new User(userDTO.getFirstname(), userDTO.getLastname(), userDTO.getUsername(), userDTO.getPassword());
//         Participant participant = new Participant(participantDTO.getUsername(), participantDTO.getName(), Integer.valueOf(participantDTO.getAge()));
//         participant.setId(Integer.valueOf(participantDTO.getTestId()));

        modelOriginal.Participant participant = new modelOriginal.Participant(request.Participant.Username, request.Participant.Name,
            Int32.Parse(request.Participant.Age));
        participant.id = Int32.Parse(request.Participant.TestId);
        return participant;
    }


    public static modelOriginal.TestParticipantRelation getTestParticipantRelation(CompetitionRequest request){
        // CompetitionProtobufs.TestParticipantRelation testParticipantRelationDTO = request.getTestParticipantRelation();
        // Tuple<Integer, Integer> id = new Tuple<>(Integer.valueOf(testParticipantRelationDTO.getTestId()), Integer.valueOf(testParticipantRelationDTO.getParticipantId()));
        // TestParticipantRelation testParticipantRelation = new TestParticipantRelation(id);

        modelOriginal.TestParticipantRelation testParticipantRelation =
            new modelOriginal.TestParticipantRelation(new Tuple<int, int>(Int32.Parse(request.TestParticipantRelation.TestId),
                Int32.Parse(request.TestParticipantRelation.ParticipantId)));
        
        return testParticipantRelation;
    }

    public static modelOriginal.Test getTest(CompetitionRequest request){
        // CompetitionProtobufs.Test testDTO = request.getTest();
        // Test test = new Test(null, null);
        // test.setId(Integer.valueOf(testDTO.getTestId()));

        modelOriginal.Test test = new modelOriginal.Test(null, null);
        test.id = Int32.Parse(request.Test.TestId); 
        return test;
    }


    }
}