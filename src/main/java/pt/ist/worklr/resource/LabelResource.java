package pt.ist.worklr.resource;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.ist.worklr.domain.DataObjectLabel;
import pt.ist.worklr.domain.ProcessLabel;
import pt.ist.worklr.domain.RequestLabel;
import pt.ist.worklr.domain.Worklr;

@Path("/labels")
public class LabelResource extends WorklrResource {

    @GET
    @Path("process")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProcessLabels() {
        getRequestAuthor();
        Set<ProcessLabel> labelSet = Worklr.getInstance().getProcessLabelSet();
        return Response.ok(view(labelSet)).build();
    }

    @GET
    @Path("request")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequestLabels() {
        getRequestAuthor();
        Set<RequestLabel> labelSet = Worklr.getInstance().getRequestLabelSet();
        return Response.ok(view(labelSet)).build();
    }

    @GET
    @Path("dataobject")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataObjectLabels() {
        getRequestAuthor();
        Set<DataObjectLabel> labelSet = Worklr.getInstance().getDataObjectLabelSet();
        return Response.ok(view(labelSet)).build();
    }

}