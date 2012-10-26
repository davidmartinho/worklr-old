package pt.ist.processpedia.dto;

public class RequestDto extends DomainObjectDto {

    private ProcessDto process;
    private String subject;
    private UserDto initiator;

    public RequestDto(String id, String subject, ProcessDto process, UserDto initiator) {
        super(id);
        this.subject = subject;
        this.process = process;
        this.initiator = initiator;
    }

    public String getSubject() {
        return subject;
    }
    
    public ProcessDto getProcess() {
        return process;
    }

    public UserDto getInitiator() {
        return initiator;
    }
}