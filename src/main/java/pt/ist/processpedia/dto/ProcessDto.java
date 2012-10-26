package pt.ist.processpedia.dto;

public class ProcessDto extends DomainObjectDto {

    private String title;

    public ProcessDto(String id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}