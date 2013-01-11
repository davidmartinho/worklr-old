package pt.ist.worklr.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.ist.worklr.domain.User;

@Path("/profile")
public class ProfileResource extends WorklrResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfile() {
	User requestAuthor = getRequestAuthor();
	return Response.ok().entity(loadJsonStringForObject(requestAuthor)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(@QueryParam("name")
    String name, @QueryParam("email")
    String email) {
	User requestAuthor = getRequestAuthor();
	requestAuthor.setName(name);
	requestAuthor.setEmail(email);
	return getProfile();
    }

}
