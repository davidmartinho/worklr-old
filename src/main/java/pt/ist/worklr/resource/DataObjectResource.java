package pt.ist.worklr.resource;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.ist.worklr.domain.DataObject;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.serializer.DataObjectAdapter;

import com.google.gson.JsonObject;

@Path("data")
public class DataObjectResource extends WorklrResource {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataObject(@PathParam("id") String externalId) {
        User author = getRequestAuthor();
        DataObject dataObject = readDomainObject(externalId);
        author.validateAccess(dataObject);
        return Response.ok(view(dataObject)).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDataObject(@PathParam("id") String externalId, JsonObject jsonObject) {
        String requestAuthorId = getRequestAuthorId();
        jsonObject.addProperty(DataObjectAdapter.AUTHOR_ID, requestAuthorId);
        DataObject dataObject = readDomainObject(externalId);
        update(jsonObject, dataObject);
        return Response.ok(view(dataObject)).build();
    }
}
