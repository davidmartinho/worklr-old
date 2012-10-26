package pt.ist.processpedia.dto;

public class CreateProcessTagDto {

    private String labelId;
    private String processId;

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }
    
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getLabelId() {
        return labelId;
    }
    
    public String getProcessId() {
        return processId;
    }

}