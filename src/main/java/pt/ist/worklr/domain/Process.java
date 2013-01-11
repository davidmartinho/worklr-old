package pt.ist.worklr.domain;

import java.util.HashSet;
import java.util.Set;

public class Process extends Process_Base {

    /**
     * Creates a new process with the given title label and associates the
     * provided user as its initiator.
     * 
     * @param titleLabel
     *            the label representing the title of the process
     * @param initiator
     *            the user that initiated the process
     */
    public Process(ProcessLabel titleLabel, User initiator) {
	setWorklr(Worklr.getInstance());
	setTitle(titleLabel);
	setInitiator(initiator);
    }

    /**
     * Creates a new top-request within the process
     * 
     * @param subjectLabel
     *            the label identifying the request
     * @param initiator
     *            the user initiating the request
     * @return the created top-request
     */
    public Request createNewRequest(RequestLabel subjectLabel, User initiator) {
	return new Request(subjectLabel, this, initiator);
    }

    /**
     * Obtains a set of users participating in the process, including its
     * initiator and all the initiators and executors of its requests.
     * 
     * @return the set of users participating in the process
     */
    public Set<User> getParticipants() {
	Set<User> participants = new HashSet<User>();
	participants.add(getInitiator());
	for (Request request : getRequestSet()) {
	    participants.add(request.getInitiator());
	    if (request.hasExecutor()) {
		participants.add(request.getExecutor());
	    }
	}
	return participants;
    }
}
