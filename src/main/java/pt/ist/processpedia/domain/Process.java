package pt.ist.processpedia.domain;

import java.util.Set;
import java.util.HashSet;

public class Process extends Process_Base {
    
    public Process(String title, User initiator) {
        setProcesspedia(Processpedia.getInstance());
        setTitle(title);
        setInitiator(initiator);
    }
    
    public Request createNewRequest(String subject, User initiator) {
        return new Request(subject, this, initiator);
    }
    
    public Set<User> getParticipants() {
        Set<User> participants = new HashSet<User>();
        return participants;
    }
        
}
