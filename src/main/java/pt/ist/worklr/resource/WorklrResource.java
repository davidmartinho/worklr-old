package pt.ist.worklr.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import pt.ist.fenixframework.DomainObject;
import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.domain.Worklr;
import pt.ist.worklr.exception.WorklrError;
import pt.ist.worklr.exception.WorklrException;
import pt.ist.worklr.serializer.WorklrGsonBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public abstract class WorklrResource {

    protected static WorklrGsonBuilder builder;

    static {
	builder = new WorklrGsonBuilder();
    }

    @Context
    private SecurityContext securityContext;

    public User getRequestAuthor() {
	if (securityContext.getUserPrincipal().getName() == null) {
	    throw new WorklrException(WorklrError.USER_NOT_AUTHORIZED);
	}
	return Worklr.fromExternalId(securityContext.getUserPrincipal().getName());
    }

    protected String loadJsonStringForObject(Object object) {
	return builder.build(object);
    }

    protected String loadJsonStringFromExternalId(String externalId) {
	return loadJsonStringForObject(readDomainObject(externalId));
    }

    protected <T extends DomainObject> T readDomainObject(final String externalId) {
	boolean error = false;
	try {
	    T obj = AbstractDomainObject.fromExternalId(externalId);
	    if (obj == null) {
		error = true;
	    } else {
		return obj;
	    }
	} catch (Throwable t) {
	    error = true;
	} finally {
	    if (error) {
		throw new WorklrException(WorklrError.RESOURCE_NOT_FOUND);
	    }
	}
	return null;
    }

    protected <T extends DomainObject> Set<T> readDomainObjectSet(final String[] externalIds) {
	Set<T> result = new HashSet<T>();
	for (String externalId : externalIds) {
	    T t = readDomainObject(externalId);
	    result.add(t);
	}
	return result;
    }

    protected String[] loadArrayOfStrings(JsonArray jsonArray) {
	String[] array = new String[jsonArray.size()];
	int i = 0;
	while (jsonArray.iterator().hasNext()) {
	    array[i++] = jsonArray.iterator().next().getAsString();
	}
	return array;
    }

    protected JsonElement parseJson(String jsonData) {
	return builder.parseJson(jsonData);
    }

}