package pt.ist.processpedia.dto;

public abstract class DomainObjectDto {

    private String id;

    public DomainObjectDto(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
}