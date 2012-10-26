package pt.ist.processpedia.dto;

public abstract class UpdateDomainObjectDto {

    private String id;
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
}