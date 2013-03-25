package pt.ist.worklr.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import pt.ist.worklr.domain.exception.WorklrDomainException;

public class Process extends Process_Base {

    /**
     * Creates a new process instance, and a root request where the initiator of the process
     * is both the initiator and executor of that request.
     * 
     * @param title the title of the process
     * @param description the description of the process
     * @param initiator the initiator of the process
     */
    public Process(String title, String description, User initiator) {
        ProcessLabel titleLabel = Worklr.getInstance().getProcessLabel(title);
        setTitle(titleLabel);
        setDescription(description);
        setInitiator(initiator);
        setCreationTimestamp(new DateTime());
        setLastUpdateTimestamp(new DateTime());
        Request rootRequest = new Request(title, description, initiator, this);
        rootRequest.setExecutor(initiator);
        setRootRequest(rootRequest);
        setWorklr(Worklr.getInstance());
    }

    /**
     * Obtains the number of executing requests.
     * 
     * @return the number of executing requests
     */
    public int getNumExecutingRequests() {
        int numExecutingRequests = 0;
        for (Request request : getRequestSet()) {
            if (request.isExecuting()) {
                numExecutingRequests++;
            }
        }
        return numExecutingRequests;
    }

    /**
     * Obtains the number of completed requests.
     * 
     * @return the number of completed requests.
     */
    public int getNumCompletedRequests() {
        int numCompletedRequests = 0;
        for (Request request : getRequestSet()) {
            if (request.isResponded()) {
                numCompletedRequests++;
            }
        }
        return numCompletedRequests;
    }

    /**
     * Obtains the number of canceled requests.
     * 
     * @return the number of canceled requests.
     */
    public int getNumCanceledRequests() {
        int numCanceledRequests = 0;
        for (Request request : getRequestSet()) {
            if (request.isCanceled()) {
                numCanceledRequests++;
            }
        }
        return numCanceledRequests;
    }

    /**
     * Obtains a set of users participating in the process, including its
     * initiator and all the initiators and executors of the process requests.
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

    /**
     * Attempts to complete the process in behalf of a given user.
     * 
     * @param user the user attempting to complete the process
     * @throws WorklrDomainException when the user does not have permission to
     *             access the process, or if any of its non-canceled request isn't responded yet.
     */
    public void complete(User user) {
        if (user.canAccess(this)) {
            if (canBeCompleted()) {
                setCompletionTimestamp(new DateTime());
                ProcessGoal.identifyAndAssociateTo(this);
            } else {
                throw WorklrDomainException.cannotCompleteProcess();
            }
        } else {
            throw WorklrDomainException.forbiddenResource();
        }
    }

    /**
     * Checks if the process is completed
     * 
     * @return true if the process is completed, false otherwise
     */
    public boolean isCompleted() {
        return getCompletionTimestamp() != null;
    }

    public Set<RequestLabel> getRequestLabelSet() {
        Set<RequestLabel> requestLabelSet = new HashSet<RequestLabel>();
        for (Request request : getRequestSet()) {
            requestLabelSet.add(request.getSubject());
        }
        return requestLabelSet;
    }

    public Set<DataObjectLabel> getDataObjectLabelSet() {
        Set<DataObjectLabel> dataObjectLabelSet = new HashSet<DataObjectLabel>();
        for (Request request : getRequestSet()) {
            Set<DataObjectLabel> requestDataObjectLabelSet = request.getDataObjectLabelSet();
            dataObjectLabelSet.addAll(requestDataObjectLabelSet);
        }
        return dataObjectLabelSet;
    }

    /**
     * Checks if the process has achieved a given process goal.
     * 
     * @param processGoal the process goal to verify the achievement
     * @return true if the process has achieved the given goal, false otherwise.
     */
    public boolean achieved(ProcessGoal processGoal) {
        return hasAchievedGoal() && getAchievedGoal().equals(processGoal);
    }

    public boolean canBeCompleted() {
        if (!isCompleted()) {
            for (Request request : getRequestSet()) {
                if (!request.isCanceled() && !request.isResponded()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

}
