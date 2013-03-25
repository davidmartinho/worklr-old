package pt.ist.worklr.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.ist.worklr.domain.AuthToken;
import pt.ist.worklr.domain.Worklr;

import com.google.gson.JsonObject;

@Path("login")
public class LoginResource extends WorklrResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(JsonObject jsonObject) {
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();
        String remoteAddress = getRequest().getRemoteAddr();
        AuthToken authToken = Worklr.getInstance().login(email, password, remoteAddress);
        return Response.status(Status.CREATED).entity(view(authToken)).build();
    }
}
