package pt.ist.worklr.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.ist.worklr.domain.AuthToken;
import pt.ist.worklr.domain.Worklr;

@Path("login")
public class LoginResource extends WorklrResource {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(@FormParam("email")
    String email, @FormParam("password")
    String password, @Context
    HttpServletRequest hsr) {
	String ipAddress = hsr.getRemoteAddr();
	System.out.println("Authenticating: " + email + " - " + password);
	AuthToken authToken = Worklr.getInstance().login(email, password, ipAddress);
	return Response.status(Status.CREATED).entity(loadJsonStringForObject(authToken)).build();
    }
}
