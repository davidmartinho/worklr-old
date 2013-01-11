package pt.ist.worklr.resource;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.ist.worklr.domain.Role;
import pt.ist.worklr.domain.Worklr;

@Path("/roles")
public class RoleResource extends WorklrResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoles() {
	getRequestAuthor();
	Set<Role> roleSet = Worklr.getInstance().getRoleSet();
	return Response.ok().entity(loadJsonStringForObject(roleSet)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRole(@FormParam("roleName")
    String roleName) {
	getRequestAuthor();
	Role role = Worklr.getInstance().createRole(roleName);
	return Response.status(Status.CREATED).entity(loadJsonStringForObject(role)).build();
    }
}
