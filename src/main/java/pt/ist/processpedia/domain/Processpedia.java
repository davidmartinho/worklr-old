package pt.ist.processpedia.domain;

import pt.ist.fenixframework.FenixFramework;

public class Processpedia extends Processpedia_Base {

    public Processpedia() {}

    public User login(String email, String password) {
        for(User user : getUserSet()) {
            if(user.getEmail().equals(email)) {
                if(user.getPassword().equals(password)) {
                    return user;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public static Processpedia getInstance() {
        return FenixFramework.getRoot();
    }

}
