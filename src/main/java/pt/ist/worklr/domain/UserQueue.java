package pt.ist.worklr.domain;

public class UserQueue extends UserQueue_Base {

    public UserQueue(User user) {
        init(user.getName());
        setUser(user);
    }

}
