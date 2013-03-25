package pt.ist.worklr.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

public class RequestTemplate extends RequestTemplate_Base {

    public RequestTemplate(Request request) {
        setCreationTimestamp(new DateTime());
        defineInputDataObjectLabelSet(request.getInputDataObjectLabelSet());
        defineCreatedDataObjectLabelSet(request.getCreatedDataObjectLabelSet());
        defineResponseDataObjectLabelSet(request.getResponseDataObjectLabelSet());
        if (request.hasParentRequest()) {
            defineRequestDataObjectLabelContextSet(request.getParentRequest().getDataObjectLabelSet());
        } else if (request.hasPreviousRequest()) {
            defineRequestDataObjectLabelContextSet(request.getPreviousRequest().getDataObjectLabelSet());
        }
        defineProcessDataObjectLabelContextSet(request.getProcess().getDataObjectLabelSet());
        defineInitiatorRoleContextSet(request.getInitiator().getRoleSet());
        defineExecutorQueueSet(request.getQueueSet());
        setWorklr(Worklr.getInstance());
    }

    private void defineRequestDataObjectLabelContextSet(Set<DataObjectLabel> requestDataObjectLabelContextSet) {
        for (DataObjectLabel requestDataObjectLabelContext : requestDataObjectLabelContextSet) {
            addRequestDataObjectLabelContext(requestDataObjectLabelContext);
        }
    }

    private void defineProcessDataObjectLabelContextSet(Set<DataObjectLabel> processDataObjectLabelContextSet) {
        for (DataObjectLabel processDataObjectLabelContext : processDataObjectLabelContextSet) {
            addProcessDataObjectLabelContext(processDataObjectLabelContext);
        }
    }

    public Set<RequestLabel> getPossibleSubjectSet() {
        Set<RequestLabel> possibleSubjectSet = new HashSet<RequestLabel>();
        for (Request request : getRequestSet()) {
            possibleSubjectSet.add(request.getSubject());
        }
        return possibleSubjectSet;
    }

    private void defineInputDataObjectLabelSet(Set<DataObjectLabel> inputDataObjectLabelSet) {
        for (DataObjectLabel inputDataObjectLabel : inputDataObjectLabelSet) {
            addInputDataObjectLabel(inputDataObjectLabel);
        }
    }

    private void defineCreatedDataObjectLabelSet(Set<DataObjectLabel> createdDataObjectLabelSet) {
        for (DataObjectLabel createdDataObjectLabel : createdDataObjectLabelSet) {
            addCreatedDataObjectLabel(createdDataObjectLabel);
        }
    }

    private void defineResponseDataObjectLabelSet(Set<DataObjectLabel> responseDataObjectLabelSet) {
        for (DataObjectLabel responseDataObjectLabel : responseDataObjectLabelSet) {
            addResponseDataObjectLabel(responseDataObjectLabel);
        }
    }

    private void defineInitiatorRoleContextSet(Set<Role> roleSet) {
        for (Role initiatorRole : roleSet) {
            addInitiatorRoleContext(initiatorRole);
        }
    }

    private void defineExecutorQueueSet(Set<Queue> executorQueueSet) {
        for (Queue queue : executorQueueSet) {
            addExecutorQueueContext(queue);
        }
    }

    public int getOccurrences(ProcessGoal processGoal) {
        int count = 0;
        for (Request request : getRequestSet()) {
            if (request.getProcess().achieved(processGoal)) {
                count++;
            }
        }
        return count;
    }

    public static void identifyAndAssociateTo(Request request) {
        RequestTemplate oldTemplate = request.getTemplate();
        for (RequestTemplate requestTemplate : Worklr.getInstance().getRequestTemplateSet()) {
            if (requestTemplate.matches(request)) {
                System.out.println("FOUND A MATCH");
                request.setTemplate(requestTemplate);
                oldTemplate.cleanIfNeeded();
                return;
            }
        }
        if (oldTemplate != null && oldTemplate.getRequestCount() == 1) {
            oldTemplate.delete();
        }
        RequestTemplate newRequestTemplate = new RequestTemplate(request);
        request.setTemplate(newRequestTemplate);
    }

    private void cleanIfNeeded() {
        if (getRequestCount() == 0) {
            delete();
        }
    }

    private void delete() {
        for (DataObjectLabel inputDataObjectLabel : getInputDataObjectLabelSet()) {
            inputDataObjectLabel.removeRequestTemplateInputContext(this);
        }
        for (DataObjectLabel createdDataObjectLabel : getCreatedDataObjectLabel()) {
            createdDataObjectLabel.removeRequestTemplateCreationContext(this);
        }
        for (DataObjectLabel responseDataObjectLabel : getResponseDataObjectLabel()) {
            responseDataObjectLabel.removeRequestTemplateResponseContext(this);
        }
        for (Role initiatorRole : getInitiatorRoleContextSet()) {
            initiatorRole.removeRequestTemplateInitiatorRoleContext(this);
        }
        for (Queue executorQueue : getExecutorQueueContextSet()) {
            executorQueue.removeRequestTemplateExecutorQueueContext(this);
        }
        for (DataObjectLabel processDataObjectLabel : getProcessDataObjectLabelContextSet()) {
            processDataObjectLabel.removeRequestTemplateProcessContext(this);
        }
        for (DataObjectLabel requestDataObjectLabel : getRequestDataObjectLabelContextSet()) {
            requestDataObjectLabel.removeRequestTemplateRequestContext(this);
        }
        for (Request request : getRequestSet()) {
            request.removeTemplate();
        }
        removeWorklr();
        deleteDomainObject();
    }

    private boolean matches(Request request) {
        System.out.println();
        System.out.println("Attempting to match request to request template...");
        System.out.println("Request Label: " + request.getSubject());
        System.out.println("Request Template Labels: " + Arrays.toString(getPossibleSubjectSet().toArray()));
        System.out.println("Request Input: " + Arrays.toString(request.getInputDataObjectLabelSet().toArray()));
        System.out.println("Request Template Input: " + Arrays.toString(getInputDataObjectLabelSet().toArray()));
        System.out.println("Request Created: " + Arrays.toString(request.getCreatedDataObjectSet().toArray()));
        System.out.println("Request Template Created: " + Arrays.toString(getCreatedDataObjectLabelSet().toArray()));
        System.out.println("Request Response: " + Arrays.toString(request.getResponseDataObjectSet().toArray()));
        System.out.println("Request Template Response: " + Arrays.toString(getResponseDataObjectLabelSet().toArray()));

        if (request.hasParentRequest()) {
            System.out.println("Request Context Data: "
                    + Arrays.toString(request.getParentRequest().getDataObjectLabelSet().toArray()));
        } else if (request.hasPreviousRequest()) {
            System.out.println("Request Context Data: "
                    + Arrays.toString(request.getPreviousRequest().getDataObjectLabelSet().toArray()));
        } else {
            System.out.println("Request Context Data: []");
        }
        System.out.println("Request Template Context Data: " + Arrays.toString(getRequestDataObjectLabelContextSet().toArray()));

        System.out.println("Process Context Data: " + Arrays.toString(request.getProcess().getDataObjectLabelSet().toArray()));
        System.out.println("Process Template Context Data: " + Arrays.toString(getProcessDataObjectLabelContextSet().toArray()));

        System.out.println("Request Initiator Roles: " + Arrays.toString(request.getInitiator().getRoleSet().toArray()));
        System.out.println("Request Template Initiator Roles: " + Arrays.toString(getInitiatorRoleContextSet().toArray()));
        System.out.println("Request Queues: " + Arrays.toString(request.getQueueSet().toArray()));
        System.out.println("Request Template Queues: " + Arrays.toString(getExecutorQueueContextSet().toArray()));

        return getInputDataObjectLabelSet().equals(request.getInputDataObjectLabelSet())
                && getCreatedDataObjectLabelSet().equals(request.getCreatedDataObjectLabelSet())
                && getResponseDataObjectLabelSet().equals(request.getResponseDataObjectSet())
                && (!request.hasParentRequest() || getRequestDataObjectLabelContextSet().equals(
                        request.getParentRequest().getDataObjectLabelSet()))
                && (!request.hasPreviousRequest() || getRequestDataObjectLabelContextSet().equals(
                        request.getPreviousRequest().getDataObjectLabelSet()))
                && getProcessDataObjectLabelContextSet().equals(request.getProcess().getDataObjectLabelSet())
                && getInitiatorRoleContextSet().equals(request.getInitiator().getRoleSet())
                && getExecutorQueueContextSet().equals(request.getQueueSet());
    }
}
