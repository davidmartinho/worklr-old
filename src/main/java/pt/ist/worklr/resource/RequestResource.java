package pt.ist.worklr.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
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

import pt.ist.worklr.domain.Commentary;
import pt.ist.worklr.domain.DataObject;
import pt.ist.worklr.domain.DataObjectType;
import pt.ist.worklr.domain.Recommendation;
import pt.ist.worklr.domain.Request;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.domain.exception.WorklrDomainException;
import pt.ist.worklr.serializer.CommentaryAdapter;
import pt.ist.worklr.serializer.DataObjectAdapter;
import pt.ist.worklr.serializer.RequestAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/requests")
public class RequestResource extends WorklrResource {

    @GET
    @Path("{requestId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequest(@PathParam("requestId") String requestId) {
        User author = getRequestAuthor();
        Request request = readDomainObject(requestId);
        if (author.canAccess(request)) {
            return Response.ok().entity(view(request)).build();
        } else {
            throw WorklrDomainException.forbiddenResource();
        }
    }

    @POST
    @Path("{requestId}/subrequest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRequest(@PathParam("requestId") String requestId, JsonObject jsonObject) {
        String authorId = getRequestAuthorId();
        jsonObject.addProperty(RequestAdapter.INITIATOR_ID, authorId);
        jsonObject.addProperty(RequestAdapter.PARENT_REQUEST_ID, requestId);
        jsonObject.addProperty(RequestAdapter.TYPE_FORWARD, false);
        Request subRequest = create(jsonObject, Request.class);
        return Response.status(Status.CREATED).entity(view(subRequest)).build();
    }

    @POST
    @Path("{requestId}/forward")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response forwardRequest(@PathParam("requestId") String requestId, JsonObject jsonObject) {
        String requestAuthorId = getRequestAuthorId();
        jsonObject.addProperty(RequestAdapter.INITIATOR_ID, requestAuthorId);
        jsonObject.addProperty(RequestAdapter.PARENT_REQUEST_ID, requestId);
        jsonObject.addProperty(RequestAdapter.TYPE_FORWARD, true);
        Request forwardRequest = create(jsonObject, Request.class);
        return Response.status(Status.CREATED).entity(view(forwardRequest)).build();
    }

    @POST
    @Path("{requestId}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response comment(@PathParam("requestId") String requestId, JsonObject jsonObject) {
        String authorId = getRequestAuthorId();
        jsonObject.addProperty(CommentaryAdapter.AUTHOR_ID, authorId);
        jsonObject.addProperty(CommentaryAdapter.REQUEST_ID, requestId);
        Commentary commentary = create(jsonObject, Commentary.class);
        return Response.status(Status.CREATED).entity(view(commentary)).build();
    }

    @POST
    @Path("{requestId}/claim")
    @Produces(MediaType.APPLICATION_JSON)
    public Response claimRequest(@PathParam("requestId") String requestId) {
        User claimer = getRequestAuthor();
        Request request = readDomainObject(requestId);
        claimer.claim(request);
        return Response.ok(view(request)).build();
    }

    @POST
    @Path("{requestId}/complete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response completeRequest(@PathParam("requestId") String requestId, JsonObject jsonObject) {
        User claimer = getRequestAuthor();
        Request request = readDomainObject(requestId);
        JsonArray jsonArray = jsonObject.get("outputDataObjectIds").getAsJsonArray();
        String[] dataObjectIdsArray = new String[jsonArray.size()];
        Iterator<JsonElement> iterator = jsonArray.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String dataObjectId = iterator.next().getAsString();
            System.out.println("Parsing " + dataObjectId);
            dataObjectIdsArray[i++] = dataObjectId;
        }
        Set<DataObject> outputDataObjectSet = readDomainObjectSet(dataObjectIdsArray);
        claimer.respond(request, outputDataObjectSet);
        return Response.ok(view(request)).build();
    }

    @POST
    @Path("{requestId}/data")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTextDataObject(@PathParam("requestId") String requestId, JsonObject jsonObject) {
        String requestAuthorId = getRequestAuthorId();
        jsonObject.addProperty(DataObjectAdapter.AUTHOR_ID, requestAuthorId);
        jsonObject.addProperty(DataObjectAdapter.REQUEST_ID, requestId);
        DataObject dataObject = create(jsonObject, DataObject.class);
        return Response.status(Status.CREATED).entity(view(dataObject)).build();
    }

    @POST
    @Path("{requestId}/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDataObject(@PathParam("requestId") String requestId, @FormDataParam("type") String type,
            @FormDataParam("label") String label, @FormDataParam("value") String value,
            @FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {
        User creator = getRequestAuthor();
        Request request = readDomainObject(requestId);

        DataObject createDataObject = null;

        if (type.equals("file")) {
            String uploadedFileLocation = "/tmp/worklr/" + fileDetail.getFileName();

            // save it
            writeToFile(uploadedInputStream, uploadedFileLocation);
            createDataObject = new DataObject(DataObjectType.FILE, label, request, creator, uploadedFileLocation);

        } else {
            createDataObject = new DataObject(DataObjectType.TEXT, label, request, creator, value);
        }

        return Response.ok().entity(view(createDataObject)).build();

    }

    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @PUT
    @Path("{requestId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRequest(@PathParam("requestId") String requestId, JsonObject jsonObject) {
        String requestAuthorId = getRequestAuthorId();
        jsonObject.addProperty(RequestAdapter.USER_ID, requestAuthorId);
        Request request = readDomainObject(requestId);
        update(jsonObject, request);
        return Response.ok(view(request)).build();
    }

    @GET
    @Path("{requestId}/recommendations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecommendationsFor(@PathParam("requestId") String requestId) {
        User user = getRequestAuthor();
        Request request = readDomainObject(requestId);
        List<Recommendation> recommendationList = request.getRecommendationsForUser(user);
//        List<Recommendation> recommendationList = new ArrayList<Recommendation>();
//        Set<RequestLabel> possibleSubjects = new HashSet<RequestLabel>();
//        possibleSubjects.add(Worklr.getInstance().getRequestLabel("Enviar Documento"));
//        possibleSubjects.add(Worklr.getInstance().getRequestLabel("Submeter Proposta"));
//        Set<DataObjectLabel> inputDataObjectLabels = new HashSet<DataObjectLabel>();
//        inputDataObjectLabels.add(Worklr.getInstance().getDataObjectLabel("Proposta de CAT"));
//        inputDataObjectLabels.add(Worklr.getInstance().getDataObjectLabel("Número de Aluno"));
//        Set<DataObjectLabel> creationDataObjectLabels = new HashSet<DataObjectLabel>();
//        creationDataObjectLabels.add(Worklr.getInstance().getDataObjectLabel("Aprovação"));
//        Set<DataObjectLabel> responseDataObjectLabels = new HashSet<DataObjectLabel>();
//        responseDataObjectLabels.add(Worklr.getInstance().getDataObjectLabel("Aprovação"));
//        Set<Queue> queueSet = new HashSet<Queue>();
//        queueSet.add(Queue.getQueueByName("António Rito Silva"));
//        queueSet.add(Queue.getQueueByName("DEI Professor"));
//        recommendationList.add(new Recommendation(possibleSubjects, inputDataObjectLabels, creationDataObjectLabels,
//                responseDataObjectLabels, queueSet));
        return Response.ok(view(recommendationList)).build();
    }

}