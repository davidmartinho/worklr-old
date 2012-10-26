package pt.ist.processpedia.resource;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import pt.ist.processpedia.domain.Processpedia;
import pt.ist.processpedia.domain.User;

public abstract class ProcesspediaResource {

    @Context
    private SecurityContext securityContext;

    public User getAuthor() {
        return Processpedia.fromExternalId(securityContext.getUserPrincipal().getName());
    }
}