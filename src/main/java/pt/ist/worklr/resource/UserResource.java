package pt.ist.worklr.resource;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.ist.worklr.domain.User;
import pt.ist.worklr.domain.Worklr;

@Path("/users")
public class UserResource extends WorklrResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		getRequestAuthor();
		Set<User> userSet = Worklr.getInstance().getUserSet();
		return Response.ok(view(userSet)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getUser(@PathParam("id") String externalId) {
		getRequestAuthor();
		User user = readDomainObject(externalId);
		return Response.ok(view(user)).build();
	}
	
	@GET
	@Path("{id}/activate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response activate(@PathParam("id") String externalId, @QueryParam("activationKey") String activationKey) {
		User user = readDomainObject(externalId);
		user.activate(activationKey);
		return Response.ok(view(user)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(String jsonData) {
	    User user = create(jsonData, User.class);
		return Response.status(Status.CREATED).entity(view(user)).build();
	}
}