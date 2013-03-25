package pt.ist.worklr.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import pt.ist.worklr.domain.exception.WorklrDomainException;

import com.google.common.collect.Sets;

public class Request extends Request_Base {

    public Request(String subject, String description, User initiator, Process process) {
        RequestLabel subjectLabel = Worklr.getInstance().getRequestLabel(subject);
        setSubject(subjectLabel);
        if (!StringUtils.isEmpty(description)) {
            new Commentary(description, initiator, this);
        }
        setInitiator(initiator);
        setProcess(process);
        setCreationTimestamp(new DateTime());
    }

    public Request(String subject, String description, User initiator, Process process, Set<Queue> queueSet,
            Set<DataObject> inputDataObjectSet) {
        this(subject, description, initiator, process);
        publish(queueSet);
        defineInputDataObjectSet(inputDataObjectSet);
        RequestTemplate.identifyAndAssociateTo(this);
    }

    /**
     * Defines the given user as the executor of an unclaimed request.
     * 
     * @param claimer the user attempting to claim the request
     * @return the claimed request.
     * @throws WorklrDomainException when either the request is already claimed by other user,
     *             of if the user claiming the request is also its initiator.
     */
    public Request claim(User claimer) {
        if (hasExecutor()) {
            throw WorklrDomainException.cannotClaimAlreadyClaimedRequests();
        } else if (hasInitiator(claimer)) {
            throw WorklrDomainException.cannotClaimOwnRequests();
        } else {
            setExecutor(claimer);
            setClaimTimestamp(new DateTime());
            return this;
        }
    }

    /**
     * Desassociates the executor from the promise of executing the request.
     * 
     * @param unclaimer the user attempting to unclaim the request
     * @return the unclaimed request
     * @throws WorklrDomainException when either the request is not claimed,
     *             or if the user unclaiming the request is not the executor of the request.
     */
    public Request unclaim(User unclaimer) {
        if (!hasExecutor()) {
            throw WorklrDomainException.cannotUnclaimUnclaimedRequests();
        } else if (!hasExecutor(unclaimer)) {
            throw WorklrDomainException.cannotUnclaimOthersRequests();
        } else {
            setClaimTimestamp(null);
            setExecutor(null);
            return this;
        }
    }

    /**
     * Completes the request.
     * 
     * @param responder the user attempting to respond to the request.
     * @return
     */
    public Request respond(User responder, Set<DataObject> outputDataObjectSet) {
        if (hasExecutor()) {
            if (hasExecutor(responder)) {
                if (getCreatedDataObjectSet().containsAll(outputDataObjectSet)) {
                    defineOutputDataObjectSet(outputDataObjectSet);
                    setResponseTimestamp(new DateTime());
                    return this;
                } else {
                    throw WorklrDomainException.cannotReplyWithDataObjectsYouDidntCreate();
                }
            } else {
                throw WorklrDomainException.cannotCompleteOthersRequests();
            }
        } else {
            throw WorklrDomainException.cannotCompleteUnclaimedRequests();
        }
    }

    public DataObject createDataObject(DataObjectType type, String label, User author) {
        DataObject dataObject = new DataObject(type, label, this, author);
        return dataObject;
    }

    public boolean isClaimed() {
        return getClaimTimestamp() != null;
    }

    public boolean isResponded() {
        return getResponseTimestamp() != null;
    }

    public Request createSubRequest(String subject, String description, User initiator, Set<Queue> queueSet,
            Set<DataObject> inputDataObjectSet) {
        Request request = new Request(subject, description, initiator, getProcess(), queueSet, inputDataObjectSet);
        return request;
    }

    private void defineInputDataObjectSet(Set<DataObject> inputDataObjectSet) {
        for (DataObject inputDataObject : inputDataObjectSet) {
            addInputDataObject(inputDataObject);
        }
    }

    private void defineOutputDataObjectSet(Set<DataObject> outputDataObjectSet) {
        for (DataObject outputDataObject : outputDataObjectSet) {
            addResponseDataObject(outputDataObject);
        }
    }

    private void publish(Set<Queue> queueSet) {
        for (Queue queue : queueSet) {
            addQueue(queue);
        }
    }

    /**
     * Computers a list of recommendations for sub-requests of the current request.
     * 
     * @param user
     *            the user executing the request
     * @return a list of recommendations for sub-requests
     */
    public List<Recommendation> getRecommendationsForUser(User user) {
        List<Recommendation> recommendationList = new ArrayList<Recommendation>();
        Set<ProcessGoal> processGoalSet = Worklr.getInstance().getProcessGoalSet();
        for (ProcessGoal processGoal : processGoalSet) {
            System.out.println("Process Goal: " + Arrays.toString(processGoal.getDataObjectLabelSet().toArray()));
            double goalMatch = processGoal.getGoalMatch(this.getProcess());
            System.out.println("Goal Match: " + goalMatch);
            Set<DataObjectLabel> missingDataObjectLabelSet = processGoal.getMissingDataObjectLabelSet(this.getProcess());
            System.out.println("Missing Data: " + Arrays.toString(missingDataObjectLabelSet.toArray()));
            for (DataObjectLabel missingDataObjectLabel : missingDataObjectLabelSet) {
                for (RequestTemplate requestTemplate : missingDataObjectLabel.getRequestTemplateCreationContextSet()) {
                    System.out.println("On Request Template:");
                    Set<DataObjectLabel> availableDataObjectLabelSet = getAvailableDataObjectLabelSet();
                    System.out.println("Available Data: " + Arrays.toString(availableDataObjectLabelSet.toArray()));
                    double inputFit = dataFitness(requestTemplate.getInputDataObjectLabelSet(), availableDataObjectLabelSet);
                    double outputFit = dataFitness(requestTemplate.getInputDataObjectLabelSet(), missingDataObjectLabelSet);
                    double requestFit =
                            dataFitness(requestTemplate.getRequestDataObjectLabelContextSet(), availableDataObjectLabelSet);
                    double processFit =
                            dataFitness(requestTemplate.getProcessDataObjectLabelContextSet(), getProcess()
                                    .getDataObjectLabelSet());
                    double initiatorFit = roleFitness(requestTemplate.getInitiatorRoleContextSet(), user.getRoleSet());
                    System.out.println("IF: " + inputFit + " - OF: " + outputFit + " - RF: " + requestFit + " - PF: "
                            + processFit + " - InitF: " + initiatorFit);
                    double requestFitness =
                            (Recommendation.W_INPUT * inputFit) + (Recommendation.W_OUTPUT * outputFit)
                                    + (Recommendation.W_REQUEST * requestFit) + (Recommendation.W_PROCESS * processFit)
                                    + (Recommendation.W_INITIATOR * initiatorFit);
                    int support = requestTemplate.getOccurrences(processGoal);
                    System.out.println("Support: " + support);
                    recommendationList.add(new Recommendation(requestTemplate, goalMatch, requestFitness, support));
                }
            }
        }
        Collections.sort(recommendationList);
        defineOrdering(recommendationList);
        return recommendationList;
    }

    private void defineOrdering(List<Recommendation> orderedRecommendationList) {
        int i = 1;
        for (Recommendation recommendation : orderedRecommendationList) {
            recommendation.setOrder(i++);
        }
    }

    private double roleFitness(Set<Role> roleSetA, Set<Role> roleSetB) {
        return (Sets.intersection(roleSetA, roleSetB).size()) > 0 ? 1 : 0;
    }

    private Set<DataObjectLabel> getAvailableDataObjectLabelSet() {
        Set<DataObjectLabel> availableDataObjectLabel = new HashSet<DataObjectLabel>();
        for (DataObject dataObject : getInputDataObjectSet()) {
            availableDataObjectLabel.add(dataObject.getLabel());
        }
        for (DataObject dataObject : getCreatedDataObjectSet()) {
            availableDataObjectLabel.add(dataObject.getLabel());
        }
        return availableDataObjectLabel;
    }

    private double dataFitness(Set<DataObjectLabel> dataObjectLabelSetA, Set<DataObjectLabel> dataObjectLabelSetB) {
        Set<DataObjectLabel> aMinusB = Sets.difference(dataObjectLabelSetA, dataObjectLabelSetB);
        return 1.0 / (1.0 + aMinusB.size());
    }

    public Set<Request> getPendingSubRequests() {
        Set<Request> pendingSubRequestSet = new HashSet<Request>();
        for (Request subRequest : getSubRequestSet()) {
            if (!subRequest.isResponded()) {
                pendingSubRequestSet.add(subRequest);
            }
        }
        return pendingSubRequestSet;
    }

    public boolean canBeCompleted() {
        return getPendingSubRequests().size() == 0;
    }

    public boolean isPending() {
        return getClaimTimestamp() == null;
    }

    public boolean isExecuting() {
        return (getClaimTimestamp() != null) && (getResponseTimestamp() == null);
    }

    public boolean isCanceled() {
        return getCancelationTimestamp() != null;
    }

    /**
     * Attempts to cancel a request in behalf of a given user. However, only
     * the initiator of the request may cancel the request.
     * 
     * @param canceler the user attempting to cancel the request
     * @throws WorklrDomainException when the user is not the initiator of the request.
     */
    public void cancel(User canceler) {
        if (hasInitiator(canceler)) {
            setCancelationTimestamp(new DateTime());
        } else {
            throw WorklrDomainException.cannotCancelOthersRequests();
        }
    }

    public boolean hasInitiator(User user) {
        return hasInitiator() && getInitiator().equals(user);
    }

    public boolean hasExecutor(User user) {
        return hasExecutor() && getExecutor().equals(user);
    }

    /**
     * Checks if a given user is a participant (initiator or executor) of the request.
     * 
     * @param user the user to be checked as a participant
     * @return true if he is a participant, false otherwise
     */
    public boolean hasParticipant(User user) {
        return user != null && (hasInitiator(user) || hasExecutor(user));
    }

    /**
     * Associates a new commentary to the request.
     * 
     * @param text the text of the commentary
     * @param author the author of the commentary
     * @return the created commentary
     * @throws WorklrDomainException when the author does not participate
     *             in the request
     */
    public Commentary comment(String text, User author) {
        if (hasParticipant(author)) {
            Commentary commentary = new Commentary(text, author, this);
            return commentary;
        } else {
            throw WorklrDomainException.cannotCommentOthersRequests();
        }
    }

    /**
     * Checks wether or not a request has been forwarded.
     * 
     * @return true if it has a forward request, false otherwise
     */
    public boolean isForwarded() {
        return hasForwardRequest();
    }

    public Set<DataObjectLabel> getDataObjectLabelSet() {
        Set<DataObjectLabel> requestDataObjectLabelSet = new HashSet<DataObjectLabel>();
        for (DataObject inputDataObject : getInputDataObjectSet()) {
            requestDataObjectLabelSet.add(inputDataObject.getLabel());
        }
        for (DataObject createdDataObject : getCreatedDataObjectSet()) {
            requestDataObjectLabelSet.add(createdDataObject.getLabel());
        }
        for (DataObject responseDataObject : getResponseDataObjectSet()) {
            requestDataObjectLabelSet.add(responseDataObject.getLabel());
        }
        return requestDataObjectLabelSet;
    }

    /**
     * Obtains the set of data labels associated to the data objects provided as input of the request.
     * 
     * @return the set of data labels associated to the input data objects
     */
    public Set<DataObjectLabel> getInputDataObjectLabelSet() {
        Set<DataObjectLabel> inputDataObjectLabelSet = new HashSet<DataObjectLabel>();
        for (DataObject inputDataObject : getInputDataObjectSet()) {
            inputDataObjectLabelSet.add(inputDataObject.getLabel());
        }
        return inputDataObjectLabelSet;
    }

    /**
     * Obtains the set of data labels associated to the data objects created within the request.
     * 
     * @return the set of data labels associated to the created data objects
     */
    public Set<DataObjectLabel> getCreatedDataObjectLabelSet() {
        Set<DataObjectLabel> createdDataObjectLabelSet = new HashSet<DataObjectLabel>();
        for (DataObject createdDataObject : getCreatedDataObjectSet()) {
            createdDataObjectLabelSet.add(createdDataObject.getLabel());
        }
        return createdDataObjectLabelSet;
    }

    /**
     * Obtains the set of data labels associated to the data objects responded within the request.
     * 
     * @return the set of data labels associated to the responded data objects
     */
    public Set<DataObjectLabel> getResponseDataObjectLabelSet() {
        Set<DataObjectLabel> responseDataObjectLabelSet = new HashSet<DataObjectLabel>();
        for (DataObject responseDataObject : getResponseDataObjectSet()) {
            responseDataObjectLabelSet.add(responseDataObject.getLabel());
        }
        return responseDataObjectLabelSet;
    }

    public void updateTemplate() {
        RequestTemplate.identifyAndAssociateTo(this);
    }
}
