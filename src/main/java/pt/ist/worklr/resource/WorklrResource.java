package pt.ist.worklr.resource;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.fenixframework.DomainObject;
import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.domain.Worklr;
import pt.ist.worklr.domain.exception.WorklrDomainException;
import pt.ist.worklr.utils.JsonAwareResource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public abstract class WorklrResource extends JsonAwareResource {

    private static Logger LOG = LoggerFactory.getLogger(WorklrResource.class);

    @Context
    private HttpServletRequest request;

    @Context
    private SecurityContext securityContext;

    protected User getRequestAuthor() {
        if (securityContext.getUserPrincipal() == null || securityContext.getUserPrincipal().getName() == null) {
            LOG.debug("Request author is not set");
            throw WorklrDomainException.unauthorized();
        }
        User requestAuthor = Worklr.fromExternalId(securityContext.getUserPrincipal().getName());
        return requestAuthor;
    }

    protected String getRequestAuthorId() {
        if (securityContext.getUserPrincipal() == null || securityContext.getUserPrincipal().getName() == null) {
            LOG.debug("Request author is not set");
            throw WorklrDomainException.unauthorized();
        }
        return securityContext.getUserPrincipal().getName();
    }

    protected HttpServletRequest getRequest() {
        return this.request;
    }

    public static <T extends DomainObject> T readDomainObject(final String externalId) {
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
                throw WorklrDomainException.resourceNotFound(externalId);
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
        Iterator<JsonElement> it = jsonArray.iterator();
        while (it.hasNext()) {
            array[i++] = it.next().getAsString();
        }
        return array;
    }

}