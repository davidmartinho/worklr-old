package pt.ist.worklr.domain.exception;

import javax.ws.rs.core.Response.Status;

import pt.ist.worklr.utils.BundleUtil;

import com.google.gson.JsonObject;

public class DomainException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String key;

    private final String[] args;

    private final String bundle;

    private final Status status;

    protected DomainException(String bundle, String key, String... args) {
        this(Status.INTERNAL_SERVER_ERROR, bundle, key, args);
    }

    protected DomainException(Status status, String bundle, String key, String... args) {
        super(key);
        this.status = status;
        this.bundle = bundle;
        this.key = key;
        this.args = args;
    }
    
    protected DomainException(Throwable cause, String bundle, String key, String... args) {
        this(cause, Status.INTERNAL_SERVER_ERROR, bundle, key, args);
    }

    protected DomainException(Throwable cause, Status status, String bundle, String key, String... args) {
        super(key, cause);
        this.status = status;
        this.bundle = bundle;
        this.key = key;
        this.args = args;
    }

    @Override
    public String getLocalizedMessage() {
        return BundleUtil.getString(bundle, key, args);
    }

    public Status getResponseStatus() {
        return status;
    }

    public JsonObject asJson() {
        JsonObject json = new JsonObject();
        json.addProperty("message", getLocalizedMessage());
        return json;
    }

    
    
}
