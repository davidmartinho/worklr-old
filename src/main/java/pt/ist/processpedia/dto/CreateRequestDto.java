package pt.ist.processpedia.dto;

public class CreateRequestDto {

    private String subject;
    private String processId;

    public void setSubject(String subject, String processId) {
        this.subject = subject;
        this.processId = processId;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public String getProcessId() {
        return processId;
    }
}