package pt.ist.worklr.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.ist.worklr.domain.User;

import com.google.gson.JsonElement;

@Path("/profile")
public class ProfileResource extends WorklrResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfile() {
		User requestAuthor = getRequestAuthor();
		return Response.ok(view(requestAuthor)).build();
	}

	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response updateProfile(JsonElement jsonElement) {
		User requestAuthor = getRequestAuthor();
		update(jsonElement, requestAuthor);
		return Response.ok(view(requestAuthor)).build();
	}

}
