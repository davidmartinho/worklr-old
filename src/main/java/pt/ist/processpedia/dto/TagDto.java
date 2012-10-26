package pt.ist.processpedia.dto;

public abstract class TagDto extends DomainObjectDto {

    private LabelDto label;

    public TagDto(String id, LabelDto label) {
        super(id);
        this.label = label;
    }

    public LabelDto getLabel() {
        return label;
    }
        
}