package pt.ist.worklr.resource;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.ist.worklr.domain.Request;
import pt.ist.worklr.domain.User;

@Path("/folders")
public class FolderResource extends WorklrResource {

    @GET
    @Path("inbox")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInboxRequests() {
	User author = getRequestAuthor();
	Set<Request> inboxRequestSet = author.getInboxRequestSet();
	return Response.ok().entity(loadJsonStringForObject(inboxRequestSet)).build();
    }

    @GET
    @Path("sent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSentRequests() {
	User author = getRequestAuthor();
	Set<Request> sentRequestSet = author.getSentRequestSet();
	return Response.ok().entity(loadJsonStringForObject(sentRequestSet)).build();
    }

    @GET
    @Path("completed")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompletedRequests() {
	User author = getRequestAuthor();
	Set<Request> completedRequestSet = author.getCompletedRequestSet();
	return Response.ok().entity(loadJsonStringForObject(completedRequestSet)).build();
    }
}