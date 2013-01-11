package pt.ist.worklr.resource;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.ist.worklr.domain.Process;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.domain.Worklr;
import pt.ist.worklr.exception.WorklrError;
import pt.ist.worklr.exception.WorklrException;

@Path("/processes")
public class ProcessResource extends WorklrResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProcesses() {
	getRequestAuthor();
	Set<Process> processes = Worklr.getInstance().getProcessSet();
	return Response.ok().entity(loadJsonStringForObject(processes)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProcess(@PathParam("id")
    String externalId) {
	User author = getRequestAuthor();
	Process process = readDomainObject(externalId);
	if (author.canAccess(process)) {
	    return Response.ok().entity(loadJsonStringFromExternalId(externalId)).build();
	} else {
	    throw new WorklrException(WorklrError.FORBIDDEN_RESOURCE);
	}
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProcess(@FormParam("title")
    String title) {
	User initiator = getRequestAuthor();
	Process newProcess = Worklr.getInstance().createProcess(title, initiator);
	return Response.status(Status.CREATED).entity(loadJsonStringForObject(newProcess)).build();
    }
}