package pt.ist.worklr.domain;

import java.util.Set;

public class Recommendation {

    private final Set<String> possibleSubjects;

    private final Set<String> inputDataObjectLabels;
    private final Set<String> creationDataObjectLabels;
    private final Set<String> responseDataObjectLabels;

    private final Set<String> queueExternalIds;

    public Recommendation(Set<String> possibleSubjects, Set<String> inputDataObjectLabel, Set<String> creationDataObjectLabel,
	    Set<String> responseDataObjectLabel, Set<String> queueExternalIds) {
	this.possibleSubjects = possibleSubjects;
	this.inputDataObjectLabels = inputDataObjectLabel;
	this.creationDataObjectLabels = creationDataObjectLabel;
	this.responseDataObjectLabels = responseDataObjectLabel;
	this.queueExternalIds = queueExternalIds;
    }

    public Set<String> getPossibleSubjects() {
	return possibleSubjects;
    }

    public Set<String> getInputDataObjectLabels() {
	return inputDataObjectLabels;
    }

    public Set<String> getCreationDataObjectLabels() {
	return creationDataObjectLabels;
    }

    public Set<String> getResponseDataObjectLabels() {
	return responseDataObjectLabels;
    }

    public Set<String> getQueueExternalIds() {
	return queueExternalIds;
    }

}
