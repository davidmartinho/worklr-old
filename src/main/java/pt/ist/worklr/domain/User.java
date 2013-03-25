package pt.ist.worklr.domain;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import pt.ist.worklr.domain.exception.WorklrDomainException;
import pt.ist.worklr.utils.CryptoUtils;

import com.google.common.hash.Hashing;

public class User extends User_Base {

    public User(String name, String email, String password, Set<Role> roleSet) {
        setName(name);
        setEmail(email);
        setSalt(CryptoUtils.generateKey());
        setPasswordHash(CryptoUtils.calculatePasswordHash(password, getSalt()));
        setActive(true);
        setActivationKey(CryptoUtils.generateKey());
        setWorklr(Worklr.getInstance());
        defineRoleSet(roleSet);
    }

    private void defineRoleSet(Set<Role> roleSet) {
        for (Role role : roleSet) {
            addRole(role);
        }
    }

    public User(String name, String email, String password, Set<Role> roleSet, boolean isAdmin) {
        setName(name);
        setEmail(email);
        setSalt(CryptoUtils.generateKey());
        setPasswordHash(CryptoUtils.calculatePasswordHash(password, getSalt()));
        setActive(true);
        setAdmin(isAdmin);
        setActivationKey(CryptoUtils.generateKey());
        setWorklr(Worklr.getInstance());
    }

    public String getAvatarUrl() {
        String hash = Hashing.md5().hashString(getEmail(), Charset.forName("UTF-8")).toString();
        return "http://www.gravatar.com/avatar/" + hash;
    }

    public Set<Request> getInboxRequestSet() {
        Set<Request> inboxRequestSet = new HashSet<Request>();
        for (Role role : getRoleSet()) {
            for (Request publishedRequest : role.getRoleQueue().getPublishedRequestSet()) {
                if (!publishedRequest.isClaimed() && !this.equals(publishedRequest.getInitiator())
                        && !publishedRequest.isCanceled()) {
                    inboxRequestSet.add(publishedRequest);
                }
            }
        }
        for (Request publishedRequest : getUserQueue().getPublishedRequest()) {
            if (!publishedRequest.isClaimed() && !this.equals(publishedRequest.getInitiator()) && !publishedRequest.isCanceled()) {
                inboxRequestSet.add(publishedRequest);
            }
        }
        return inboxRequestSet;
    }

    public Set<Request> getExecutingRequestSet() {
        Set<Request> executingRequestSet = new HashSet<Request>();
        for (Request executingRequest : getExecutedRequestSet()) {
            if (!executingRequest.isResponded() && !executingRequest.isCanceled() && !executingRequest.isForwarded()) {
                executingRequestSet.add(executingRequest);
            }
        }
        return executingRequestSet;
    }

    public Set<Request> getSentRequestSet() {
        Set<Request> sentRequestSet = new HashSet<Request>();
        for (Request initiatedRequest : getInitiatedRequestSet()) {
            if (!this.equals(initiatedRequest.getExecutor()) && !initiatedRequest.isCanceled()) {
                sentRequestSet.add(initiatedRequest);
            }
        }
        return sentRequestSet;
    }

    public Set<Request> getCompletedRequestSet() {
        Set<Request> completedRequestSet = new HashSet<Request>();
        for (Request request : getExecutedRequestSet()) {
            if (request.isResponded()) {
                completedRequestSet.add(request);
            }
        }
        return completedRequestSet;
    }

    public boolean canAccess(Process process) {
        return process.getParticipants().contains(this);
    }

    public boolean canAccess(Request request) {
        return request.getInitiator().equals(this) || (request.hasExecutor() && request.getExecutor().equals(this))
                || hasRequestAccessibleOnQueue(request);
    }

    public boolean hasRequestAccessibleOnQueue(Request request) {
        for (Role role : getRoleSet()) {
            if (role.getRoleQueue().hasPublishedRequest(request)) {
                return true;
            }
        }
        return getUserQueue().hasPublishedRequest(request);
    }

    public void claim(Request request) {
        request.claim(this);
    }

    public void respond(Request request, Set<DataObject> outputDataObjectSet) {
        request.respond(this, outputDataObjectSet);
    }

    public void activate(String activationKey) {
        if (getActive()) {
            throw WorklrDomainException.userAlreadyActive();
        } else {
            if (getActivationKey().equals(activationKey)) {
                setActive(true);
            } else {
                throw WorklrDomainException.wrongActivationKey();
            }
        }
    }

    public boolean isAdmin() {
        return getAdmin();
    }

    public void validateAccess(DataObject dataObject) {
        if (dataObject.getAuthor().equals(this)) {
            return;
        } else {
            for (Request request : dataObject.getRequestInputContextSet()) {
                if (request.hasInitiator(this) || request.hasExecutor(this)) {
                    return;
                }
            }
            for (Request request : dataObject.getRequestResponseContextSet()) {
                if (request.hasInitiator(this) || request.hasExecutor(this)) {
                    return;
                }
            }
            throw WorklrDomainException.forbiddenResource();
        }
    }

    public void changePassword(String newPassword) {
        setSalt(CryptoUtils.generateKey());
        setPasswordHash(CryptoUtils.calculatePasswordHash(newPassword, getSalt()));
    }

    public void cancel(Request request) {
        request.cancel(this);
    }

    public void copy(DataObject dataObject, Request request) {
        if (!request.hasInitiator(this)) {
            throw WorklrDomainException.forbiddenResource();
        } else if (request.getInputDataObjectSet().contains(dataObject)) {
            throw WorklrDomainException.dataObjectAlreadyProvidedAsInput();
        } else {
            request.addInputDataObject(dataObject);
        }
    }
}