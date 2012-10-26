package pt.ist.processpedia.domain;

import org.joda.time.DateTime;

public class Request extends Request_Base {
    
    public Request(String subject, Process process, User initiator) {
        setSubject(subject);
        setProcess(process);
        setInitiator(initiator);
        setCreationTimestamp(new DateTime());
    }
    
    public Request claim(User claimer) {
        setClaimTimestamp(new DateTime());
        return this;
    }
    
    public Request respond(User responder) {
        setResponseTimestamp(new DateTime());
        return this;
    }
    
    public boolean isClaimed() {
        return getClaimTimestamp() != null;
    }
    
    public boolean isResponded() {
        return getResponseTimestamp() != null;
    }
    
}
