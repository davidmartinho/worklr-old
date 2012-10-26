package pt.ist.processpedia.dto;

public class CreateRequestTagDto {

    private String labelId;
    private String requestId;

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getLabelId() {
        return labelId;
    }
    
    public String getRequestId() {
        return requestId;
    }

}