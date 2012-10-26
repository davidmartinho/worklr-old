package pt.ist.processpedia.dto;

public class RequestTagDto extends TagDto {

    private LabelDto label;
    private RequestDto request;

    public RequestTagDto(String id, LabelDto label, RequestDto request) {
        super(id, label);
        this.request = request;
    }

    public LabelDto getLabel() {
        return label;
    }
    
    public RequestDto getRequest() {
        return request;
    }
}