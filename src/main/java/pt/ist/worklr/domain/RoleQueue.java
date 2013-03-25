package pt.ist.worklr.domain;

public class RoleQueue extends RoleQueue_Base {

    public RoleQueue(Role role) {
        init(role.getRoleName());
        setRole(role);
    }

}
