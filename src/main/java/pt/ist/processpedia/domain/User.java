package pt.ist.processpedia.domain;

import java.util.Set;
import java.util.HashSet;

public class User extends User_Base {

    public User(String name, String email, String password) {
        setProcesspedia(Processpedia.getInstance());
        setName(name);
        setEmail(email);
        setPassword(password);
    }
    
    public Set<Request> getInboxRequestSet() {
        Set<Request> inboxRequestSet = new HashSet<Request>();
        return inboxRequestSet;
    }
    
    public Set<Request> getSentRequestSet() {
        Set<Request> sentRequestSet = new HashSet<Request>();
        return sentRequestSet;
    }
    
    public Set<Request> getCompletedRequestSet() {
        Set<Request> completedRequestSet = new HashSet<Request>();
        return completedRequestSet;
    }
}