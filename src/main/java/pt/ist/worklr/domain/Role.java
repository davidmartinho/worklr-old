package pt.ist.worklr.domain;

public class Role extends Role_Base {

    public Role(String roleName) {
        setRoleName(roleName);
    }

    @Override
    public String toString() {
        return getRoleName();
    }
}
