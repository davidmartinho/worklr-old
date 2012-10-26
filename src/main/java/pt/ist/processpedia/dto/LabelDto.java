package pt.ist.processpedia.dto;

public class LabelDto extends DomainObjectDto {

    private String label;

    public LabelDto(String id, String label) {
        super(id);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}