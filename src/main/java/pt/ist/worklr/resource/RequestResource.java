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

import pt.ist.worklr.domain.Queue;
import pt.ist.worklr.domain.Request;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.exception.WorklrError;
import pt.ist.worklr.exception.WorklrException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("/requests")
public class RequestResource extends WorklrResource {

    @GET
    @Path("{requestId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequest(@PathParam("requestId")
    String requestId) {
	User author = getRequestAuthor();
	Request request = readDomainObject(requestId);
	if (author.canAccess(request)) {
	    return Response.ok().entity(loadJsonStringForObject(request)).build();
	} else {
	    throw new WorklrException(WorklrError.FORBIDDEN_RESOURCE);
	}
    }

    @POST
    @Path("{requestId}/subrequest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRequest(@PathParam("requestId")
    String requestId, @FormParam("data")
    String jsonData) {
	User author = getRequestAuthor();
	Request parentRequest = readDomainObject(requestId);
	JsonObject jsonObject = parseJson(jsonData).getAsJsonObject();

	String subject = jsonObject.get("subject").getAsString();
	JsonArray jsonArray = jsonObject.get("queues").getAsJsonArray();
	Set<Queue> queueSet = readDomainObjectSet(loadArrayOfStrings(jsonArray));

	Request createdRequest = parentRequest.createSubRequest(subject, queueSet, author);

	return Response.status(Status.CREATED).entity(super.loadJsonStringForObject(createdRequest)).build();
    }

    @POST
    @Path("{requestId}/claim")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response claimRequest(@PathParam("requestId")
    String requestId) {
	User claimer = getRequestAuthor();
	Request request = readDomainObject(requestId);
	claimer.claim(request);
	return Response.ok().entity(loadJsonStringForObject(request)).build();
    }

    @POST
    @Path("{requestId}/respond")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response respondRequest(@PathParam("requestId")
    String requestId) {
	User claimer = getRequestAuthor();
	Request request = readDomainObject(requestId);
	claimer.respond(request);
	return Response.ok().entity(loadJsonStringForObject(request)).build();
    }

}