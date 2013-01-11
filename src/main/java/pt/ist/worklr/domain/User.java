package pt.ist.worklr.domain;

import java.util.HashSet;
import java.util.Set;

import pt.ist.worklr.exception.WorklrError;
import pt.ist.worklr.exception.WorklrException;
import pt.ist.worklr.utils.CryptoUtils;

public class User extends User_Base {

    public User(String name, String email, String password) {
	setName(name);
	setEmail(email);
	setSalt(CryptoUtils.generateKey());
	setPasswordHash(CryptoUtils.calculatePasswordHash(password, getSalt()));
	setActive(false);
	setActivationKey(CryptoUtils.generateKey());
	setAvatarUrl("http://worklr.org/api/users/" + getExternalId() + "/avatar");
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

    public boolean canAccess(Process process) {
	return process.getParticipants().contains(this);
    }

    public boolean canAccess(Request request) {
	return request.getInitiator().equals(this) || (request.hasExecutor() && request.getExecutor().equals(this));
    }

    public void claim(Request request) {
	if (request.getInitiator().equals(this)) {
	    throw new WorklrException(WorklrError.CANNOT_CLAIM_INITIATED_REQUEST);
	} else {
	    request.setExecutor(this);
	}
    }

    public void respond(Request request) {
	if (request.hasExecutor()) {
	    if (request.getExecutor().equals(this)) {
		request.respond(this);
	    } else {
		throw new WorklrException(WorklrError.CANNOT_RESPOND_TO_OTHERS_REQUESTS);
	    }
	} else {
	    throw new WorklrException(WorklrError.CANNOT_RESPOND_TO_NON_EXECUTING_REQUESTS);
	}
    }

    public void activate(String activationKey) {
	if (getActive()) {
	    throw new WorklrException(WorklrError.USER_ALREADY_ACTIVE);
	} else {
	    if (getActivationKey().equals(activationKey)) {
		setActive(true);
	    } else {
		throw new WorklrException(WorklrError.WRONG_ACTIVATON_KEY);
	    }
	}

    }
}