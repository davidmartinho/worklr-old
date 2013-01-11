package pt.ist.worklr.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

public class Request extends Request_Base {

    public Request(RequestLabel subjectLabel, Process process, User initiator) {
	setSubject(subjectLabel);
	setProcess(process);
	setInitiator(initiator);
	setCreationTimestamp(new DateTime());
    }

    public Request(RequestLabel subjectLabel, Process process, User initiator, Request parentRequest) {
	this(subjectLabel, process, initiator);
	setParentRequest(parentRequest);
    }

    public Request claim(User claimer) {
	setClaimTimestamp(new DateTime());
	setExecutor(claimer);
	return this;
    }

    public Request respond(User responder) {
	setResponseTimestamp(new DateTime());
	return this;
    }

    public DataObject createDataObject(DataObjectType type, String labelString, User author) {
	DataObjectLabel label = Worklr.getInstance().getDataObjectLabel(labelString);
	DataObject dataObject = new DataObject(type, label, this, author);
	return dataObject;
    }

    public boolean isClaimed() {
	return getClaimTimestamp() != null;
    }

    public boolean isResponded() {
	return getResponseTimestamp() != null;
    }

    public Request createSubRequest(String subject, Set<Queue> queueSet, User initiator) {
	RequestLabel subjectLabel = Worklr.getInstance().getRequestLabel(subject);
	Request request = new Request(subjectLabel, getProcess(), initiator, this);
	request.publish(queueSet);
	return request;
    }

    private void publish(Set<Queue> queueSet) {
	for (Queue queue : queueSet) {
	    addQueue(queue);
	}
    }

    public List<Recommendation> getRecommendationsForUser(User user) {
	List<Recommendation> recommendationList = new ArrayList<Recommendation>();

	// TODO: IMPLEMENT RECOMMENDATION ALGORITHM
	return recommendationList;
    }
}
