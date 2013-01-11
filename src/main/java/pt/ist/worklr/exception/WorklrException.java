package pt.ist.worklr.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public final class WorklrException extends WebApplicationException {

    private static final long serialVersionUID = 1L;

    private final WorklrError error;

    public WorklrException(WorklrError error) {
	this.error = error;
    }

    public final Status getResponseStatus() {
	return error.getResponseStatus();
    }

    public final int getErrorCode() {
	return error.getInternalErrorCode();
    }

    @Override
    public final String getMessage() {
	return error.getMessage();
    }

}
