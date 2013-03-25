package pt.ist.worklr.resource;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.ist.worklr.domain.Process;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.domain.Worklr;
import pt.ist.worklr.domain.exception.WorklrDomainException;
import pt.ist.worklr.serializer.ProcessAdapter;

import com.google.gson.JsonObject;

@Path("/processes")
public class ProcessResource extends WorklrResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProcesses() {
        User requestingUser = getRequestAuthor();
        Set<Process> processes = Worklr.getInstance().getProcessSet(requestingUser);
        return Response.ok(view(processes)).build();
    }

    @GET
    @Path("ongoing")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOngoingProcesses() {
        User requestingUser = getRequestAuthor();
        Set<Process> ongoingProcesses = Worklr.getInstance().getOngoingProcessSet(requestingUser);
        return Response.ok(view(ongoingProcesses)).build();
    }

    @GET
    @Path("completed")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompletedProcesses() {
        User requestingUser = getRequestAuthor();
        Set<Process> processes = Worklr.getInstance().getCompletedProcessSet(requestingUser);
        return Response.ok(view(processes)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProcess(@PathParam("id") String processId) {
        User author = getRequestAuthor();
        Process process = readDomainObject(processId);
        if (author.canAccess(process)) {
            return Response.ok(view(process)).build();
        } else {
            throw WorklrDomainException.forbiddenResource();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response completeProcess(@PathParam("id") String processId, JsonObject jsonObject) {
        String authorId = getRequestAuthorId();
        jsonObject.addProperty(ProcessAdapter.AUTHOR_ID, authorId);
        Process process = readDomainObject(processId);
        update(jsonObject, process);
        return Response.ok(view(process)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProcess(JsonObject jsonObject) {
        String authorId = getRequestAuthorId();
        jsonObject.addProperty(ProcessAdapter.INITIATOR_ID, authorId);
        Process process = create(jsonObject, Process.class);
        return Response.status(Status.CREATED).entity(view(process)).build();
    }
}