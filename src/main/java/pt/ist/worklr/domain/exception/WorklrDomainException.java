package pt.ist.worklr.domain.exception;

import javax.ws.rs.core.Response.Status;

public class WorklrDomainException extends DomainException {

    private static final long serialVersionUID = 1L;

    protected static final String BUNDLE = "WorklrResources";

    protected WorklrDomainException(String key, String... args) {
        super(BUNDLE, key, args);
    }

    protected WorklrDomainException(Status status, String key, String... args) {
        super(status, BUNDLE, key, args);
    }

    protected WorklrDomainException(Throwable cause, String key, String... args) {
        super(cause, BUNDLE, key, args);
    }

    protected WorklrDomainException(Throwable cause, Status status, String key, String... args) {
        super(cause, status, BUNDLE, key, args);
    }

    public static WorklrDomainException unauthorized() {
        return new WorklrDomainException(Status.UNAUTHORIZED, "unauthorized");
    }

    public static WorklrDomainException resourceNotFound(String id) {
        return new WorklrDomainException(Status.NOT_FOUND, "resource.not.found", id);
    }

    public static WorklrDomainException cannotCreateEntity() {
        return new WorklrDomainException("cannot.create.entity");
    }

    public static WorklrDomainException parseError() {
        return new WorklrDomainException(Status.BAD_REQUEST, "parse.error");
    }

    public static WorklrDomainException cannotClaimOwnRequests() {
        return new WorklrDomainException("cannot.claim.own.requests");
    }

    public static WorklrDomainException cannotClaimAlreadyClaimedRequests() {
        return new WorklrDomainException("cannot.claim.already.claimed.requests");
    }

    public static WorklrDomainException cannotUnclaimUnclaimedRequests() {
        return new WorklrDomainException("cannot.unclaim.unclaimed.requests");
    }

    public static WorklrDomainException cannotUnclaimOthersRequests() {
        return new WorklrDomainException("cannot.unclaim.others.requests");
    }

    public static WorklrDomainException cannotCompleteUnclaimedRequests() {
        return new WorklrDomainException("cannot.complete.unclaimed.requests");
    }

    public static WorklrDomainException cannotCompleteOthersRequests() {
        return new WorklrDomainException("cannot.complete.others.requests");
    }

    public static WorklrDomainException subjectCannotBeEmpty() {
        return new WorklrDomainException("subject.cannot.be.empty");
    }

    public static WorklrDomainException forbiddenResource() {
        return new WorklrDomainException("forbidden.resource");
    }

    public static WorklrDomainException emailAlreadyInUse() {
        return new WorklrDomainException("email.already.in.use");
    }

    public static WorklrDomainException cannotCommentOthersRequests() {
        return new WorklrDomainException("cannot.comment.others.requests");
    }

    public static WorklrDomainException cannotReplyWithDataObjectsYouDidntCreate() {
        return new WorklrDomainException("cannot.reply.with.data.objects.you.didnt.create");
    }

    public static WorklrDomainException invalidCredentials() {
        return new WorklrDomainException("invalid.credentials");
    }

    public static WorklrDomainException expiredAuthToken() {
        return new WorklrDomainException("expired.auth.token");
    }

    public static WorklrDomainException invalidIpAddress() {
        return new WorklrDomainException("invalid.ip.address");
    }

    public static WorklrDomainException userAlreadyActive() {
        return new WorklrDomainException("user.already.active");
    }

    public static WorklrDomainException cannotCancelOthersRequests() {
        return new WorklrDomainException("cannot.cancel.others.requests");
    }

    public static WorklrDomainException wrongActivationKey() {
        return new WorklrDomainException("wrong.activation.key");
    }

    public static WorklrDomainException cannotCompleteProcess() {
        return new WorklrDomainException("cannot.complete.process");
    }

    public static WorklrDomainException dataObjectAlreadyProvidedAsInput() {
        return new WorklrDomainException("data.object.already.provided.as.input");
    }
}
