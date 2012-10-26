package pt.ist.processpedia.dto;

public class UserDto extends DomainObjectDto {

    private String email;

    public UserDto(String id, String email) {
        super(id);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}