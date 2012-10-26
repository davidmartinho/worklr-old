package pt.ist.processpedia.service;

import pt.ist.processpedia.domain.Processpedia;
import pt.ist.processpedia.domain.Process;
import pt.ist.processpedia.domain.Request;
import pt.ist.processpedia.domain.User;
import pt.ist.processpedia.domain.Label;
import pt.ist.processpedia.dto.RespondRequestDto;
import pt.ist.processpedia.dto.ClaimRequestDto;
import pt.ist.processpedia.dto.CreateProcessDto;
import pt.ist.processpedia.dto.CreateRequestDto;
import pt.ist.processpedia.dto.CreateUserDto;
import pt.ist.processpedia.dto.UpdateUserDto;
import pt.ist.processpedia.dto.CreateLabelDto;

public class ServiceManager {
    
    public static User getUser(String userId) {
        return Processpedia.fromExternalId(userId);
    }
    
    public static User createUser(CreateUserDto createUserDto) {
        String name = createUserDto.getName();
        String email = createUserDto.getEmail();
        String password = createUserDto.getPassword();
        return new User(name, email, password);
    }
    
    public static User updateUser(UpdateUserDto updateUserDto) {
        User user = Processpedia.fromExternalId(updateUserDto.getId());
        user.setName(updateUserDto.getName());
        user.setEmail(updateUserDto.getEmail());
        user.setPassword(updateUserDto.getPassword());
        return user;
    }
    
    public static Process getProcess(String processId) {
        return Processpedia.fromExternalId(processId);
    }
    
    public static Process createProcess(CreateProcessDto createProcessDto, User initiator) {
        String title = createProcessDto.getTitle();
        return new Process(title, initiator);
    }
    
    public static Request getRequest(String requestId) {
        return Processpedia.fromExternalId(requestId);
    }
    
    public static Request createRequest(CreateRequestDto createRequestDto, User initiator) {
        String subject = createRequestDto.getSubject();
        Process process = Processpedia.fromExternalId(createRequestDto.getProcessId());
        return process.createNewRequest(subject, initiator);
    }
    
    public static Request claimRequest(ClaimRequestDto claimRequestDto, User claimer) {
        Request request = Processpedia.fromExternalId(claimRequestDto.getRequestId());
        return request.claim(claimer);
    }
    
    public static Request respondRequest(RespondRequestDto respondRequestDto, User responder) {
        Request request = Processpedia.fromExternalId(respondRequestDto.getRequestId());
        return request.respond(responder);
    }
    
    public static Label getLabel(String labelId) {
        return Processpedia.fromExternalId(labelId);
    }
    
    public static Label createLabel(CreateLabelDto createLabelDto, User author) {
        String label = createLabelDto.getLabel();
        return new Label(label, author);
    }
}