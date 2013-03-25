package pt.ist.worklr.serializer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pt.ist.bennu.json.JsonAdapter;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.worklr.domain.DataObject;
import pt.ist.worklr.domain.Queue;
import pt.ist.worklr.domain.Request;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.resource.WorklrResource;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(Request.class)
public class RequestAdapter implements JsonAdapter<Request> {

    public static final String ID = "id";
    public static final String SUBJECT = "subject";
    public static final String DESCRIPTION = "description";
    public static final String INITIATOR = "initiator";
    public static final String EXECUTOR = "executor";
    public static final String COMMENTARIES = "commentaries";
    public static final String QUEUES = "queues";

    public static final String CREATION_TIMESTAMP = "creationTimestamp";
    public static final String LAST_UPDATE_TIMESTAMP = "lastUpdateTimestamp";
    public static final String COMPLETION_TIMESTAMP = "completionTimestamp";
    public static final String CANCELATION_TIMESTAMP = "cancelationTimestamp";

    public static final String PARENT_REQUEST_ID = "parentRequestId";
    public static final String INITIATOR_ID = "initiatorId";

    public static final String INPUT_DATA_OBJECTS = "inputDataObjects";
    public static final String CREATED_DATA_OBJECTS = "createdDataObjects";
    public static final String RESPONSE_DATA_OBJECTS = "responseDataObjects";

    public static final String SUB_REQUESTS = "pendingSubRequests";
    public static final String FORWARD_REQUEST = "acceptedForwardRequest";
    public static final String TYPE_FORWARD = "typeForward";
    public static final String USER_ID = "userId";

    public static final String CLAIM = "claim";
    public static final String CANCEL = "cancel";
    public static final String RESPOND = "respond";
    public static final String PROVIDE_DATA = "provideData";
    public static final String DATA_OBJECT_ID = "dataObjectId";
    public static final String PARENT_ID = "parentId";

    @Override
    public JsonElement view(Request request, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ID, request.getExternalId());
        jsonObject.add(SUBJECT, ctx.view(request.getSubject()));
        jsonObject.addProperty(DESCRIPTION, request.getDescription());
        jsonObject.addProperty(PARENT_ID, request.getParentRequest() != null ? request.getParentRequest().getExternalId() : null);
        jsonObject.add(COMMENTARIES, ctx.view(request.getCommentarySet()));
        jsonObject.add(INITIATOR, ctx.view(request.getInitiator()));
        jsonObject.add(EXECUTOR, ctx.view(request.getExecutor()));
        jsonObject.add(CREATION_TIMESTAMP, ctx.view(request.getCreationTimestamp()));
        jsonObject.add(LAST_UPDATE_TIMESTAMP, ctx.view(request.getLastUpdateTimestamp()));
        jsonObject.add(COMPLETION_TIMESTAMP, ctx.view(request.getResponseTimestamp()));
        jsonObject.add(CANCELATION_TIMESTAMP, ctx.view(request.getCancelationTimestamp()));
        jsonObject.add(INPUT_DATA_OBJECTS, ctx.view(request.getInputDataObjectSet()));
        jsonObject.add(CREATED_DATA_OBJECTS, ctx.view(request.getCreatedDataObjectSet()));
        jsonObject.add(RESPONSE_DATA_OBJECTS, ctx.view(request.getResponseDataObjectSet()));
        jsonObject.add(SUB_REQUESTS, ctx.view(request.getSubRequestSet()));
        jsonObject.add(FORWARD_REQUEST, ctx.view(request.getForwardRequest()));
        return jsonObject;
    }

    @Override
    public Request create(JsonElement jsonElement, JsonBuilder ctx) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String subject = jsonObject.get(SUBJECT).getAsString();
        String description = jsonObject.get(DESCRIPTION).getAsString();
        JsonArray jsonQueueArray = jsonObject.get(QUEUES).getAsJsonArray();
        Iterator<JsonElement> queueIterator = jsonQueueArray.iterator();
        Set<Queue> queueSet = new HashSet<Queue>();
        while (queueIterator.hasNext()) {
            String queueExternalId = queueIterator.next().getAsString();
            Queue queue = WorklrResource.readDomainObject(queueExternalId);
            queueSet.add(queue);
        }
        JsonArray jsonInputDataObjectArray = jsonObject.get(INPUT_DATA_OBJECTS).getAsJsonArray();
        Iterator<JsonElement> inputDataObjectIterator = jsonInputDataObjectArray.iterator();
        Set<DataObject> inputDataObjectSet = new HashSet<DataObject>();
        while (inputDataObjectIterator.hasNext()) {
            String dataObjectExternalId = inputDataObjectIterator.next().getAsString();
            DataObject dataObject = WorklrResource.readDomainObject(dataObjectExternalId);
            inputDataObjectSet.add(dataObject);
        }
        String parentRequestId = jsonObject.get(PARENT_REQUEST_ID).getAsString();
        Request parentRequest = WorklrResource.readDomainObject(parentRequestId);
        String initiatorId = jsonObject.get(INITIATOR_ID).getAsString();
        User initiator = WorklrResource.readDomainObject(initiatorId);
        Request newRequest =
                new Request(subject, description, initiator, parentRequest.getProcess(), queueSet, inputDataObjectSet);
        boolean isTypeForward = jsonObject.get(TYPE_FORWARD).getAsBoolean();
        if (isTypeForward) {
            parentRequest.setForwardRequest(newRequest);
        } else {
            parentRequest.addSubRequest(newRequest);
        }
        return newRequest;
    }

    @Override
    public Request update(JsonElement jsonElement, Request request, JsonBuilder ctx) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String userId = jsonObject.get(USER_ID).getAsString();
        User user = WorklrResource.readDomainObject(userId);
        if (jsonObject.has(CLAIM) && jsonObject.get(CLAIM).getAsBoolean()) {
            user.claim(request);
            return request;
        }
        if (jsonObject.has(CANCEL) && jsonObject.get(CANCEL).getAsBoolean()) {
            user.cancel(request);
            return request;
        }
        if (jsonObject.has(RESPOND) && jsonObject.get(RESPOND).getAsBoolean()) {
            Set<DataObject> outputDataObjectSet = new HashSet<DataObject>();
            Iterator<JsonElement> outputDataObjectIterator = jsonObject.getAsJsonArray().iterator();
            while (outputDataObjectIterator.hasNext()) {
                String dataObjectId = outputDataObjectIterator.next().getAsString();
                DataObject dataObject = WorklrResource.readDomainObject(dataObjectId);
                outputDataObjectSet.add(dataObject);
            }
            user.respond(request, outputDataObjectSet);
            return request;
        }
        if (jsonObject.has(PROVIDE_DATA) && jsonObject.get(PROVIDE_DATA).getAsBoolean()) {
            String dataObjectId = jsonObject.get(DATA_OBJECT_ID).getAsString();
            DataObject dataObject = WorklrResource.readDomainObject(dataObjectId);
            user.copy(dataObject, request);
            return request;
        }
        return request;
    }
}
