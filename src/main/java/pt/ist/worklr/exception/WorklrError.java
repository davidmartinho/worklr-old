package pt.ist.worklr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public enum WorklrError {

    USER_NOT_AUTHORIZED(Response.Status.UNAUTHORIZED, 1000, "User is not authorized"), REQUEST_ALREADY_CLAIMED(
	    Response.Status.NOT_FOUND, 1001, "The request is already claimed"), LABEL_ALREADY_BEING_USED(Status.NOT_FOUND, 1002,
	    ""), INVALID_IP_ADDRESS(Response.Status.ACCEPTED, 1003,
	    "Your IP Address doesn't match. Your attempt has been logged."), INVALID_AUTH_TOKEN(Response.Status.ACCEPTED, 1004,
	    "The provided auth token is invalid."), NO_ACTIVE_AUTH_TOKEN(Status.ACCEPTED, 1005,
	    "The user has no active auth token."), RESOURCE_NOT_FOUND(Status.NOT_FOUND, 1006, "Resource not found"), CANNOT_RESPOND_TO_OTHERS_REQUESTS(
	    Status.ACCEPTED, 1007, "Cannot respond to others requests."), CANNOT_RESPOND_TO_NON_EXECUTING_REQUESTS(Status.OK,
	    1008, "Cannot respond to requests that are not being executed"), USER_ALREADY_ACTIVE(Status.OK, 1009,
	    "User is already active"), WRONG_ACTIVATON_KEY(Status.OK, 1010, "The provided activation key is wrong."), CANNOT_CLAIM_INITIATED_REQUEST(
	    Status.OK, 1011, "You cannot claim requests that you initiated"), FORBIDDEN_RESOURCE(Status.FORBIDDEN, 1012,
	    "You are forbidden to access this resource"), EXPIRED_AUTH_TOKEN(Status.OK, 1013,
	    "Your token has expired. Please login again.");

    private Status responseStatus;
    private int internalErrorCode;
    private String message;

    private WorklrError(Status responseStatus, int internalErrorCode, String message) {
	this.responseStatus = responseStatus;
	this.internalErrorCode = internalErrorCode;
	this.message = message;
    }

    public Status getResponseStatus() {
	return responseStatus;
    }

    public int getInternalErrorCode() {
	return internalErrorCode;
    }

    public String getMessage() {
	return message;
    }

}
