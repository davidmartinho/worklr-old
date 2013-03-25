package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonAdapter;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.worklr.domain.Process;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.resource.WorklrResource;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(pt.ist.worklr.domain.Process.class)
public class ProcessAdapter implements JsonAdapter<pt.ist.worklr.domain.Process> {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String INITIATOR = "initiator";
    public static final String PARTICIPANTS = "participants";

    public static final String NUM_EXECUTING_REQUESTS = "numExecutingRequests";
    public static final String NUM_COMPLETED_REQUESTS = "numCompletedRequests";
    public static final String INITIATOR_ID = "initiatorId";
    public static final String CAN_BE_COMPLETED = "canBeCompleted";

    public static final String COMPLETION_TIMESTAMP = "completionTimestamp";

    public static final String COMPLETE = "complete";
    public static final String AUTHOR_ID = "authorId";

    public static final String REQUEST_LABELS = "requestLabels";
    public static final String DATA_LABELS = "dataLabels";

    @Override
    public Process create(JsonElement jsonElement, JsonBuilder ctx) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String processTitle = jsonObject.get(TITLE).getAsString();
        String processDescription = jsonObject.get(DESCRIPTION).getAsString();
        String initiatorId = jsonObject.get(INITIATOR_ID).getAsString();
        User initiator = WorklrResource.readDomainObject(initiatorId);
        return new Process(processTitle, processDescription, initiator);
    }

    @Override
    public Process update(JsonElement json, Process process, JsonBuilder ctx) {
        JsonObject jsonObject = json.getAsJsonObject();
        String authorId = jsonObject.get(AUTHOR_ID).getAsString();
        User user = WorklrResource.readDomainObject(authorId);
        if (jsonObject.has(COMPLETE) && jsonObject.get(COMPLETE).getAsBoolean()) {
            process.complete(user);
        }
        return process;
    }

    @Override
    public JsonElement view(Process process, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ID, process.getExternalId());
        jsonObject.add(TITLE, ctx.view(process.getTitle()));
        jsonObject.addProperty(DESCRIPTION, process.getDescription());
        jsonObject.addProperty(CAN_BE_COMPLETED, process.canBeCompleted());
        jsonObject.add(COMPLETION_TIMESTAMP, ctx.view(process.getCompletionTimestamp()));
        jsonObject.add(INITIATOR, ctx.view(process.getInitiator()));
        jsonObject.addProperty(NUM_EXECUTING_REQUESTS, process.getNumExecutingRequests());
        jsonObject.addProperty(NUM_COMPLETED_REQUESTS, process.getNumCompletedRequests());
        jsonObject.add(PARTICIPANTS, ctx.view(process.getParticipants()));
        jsonObject.add(DATA_LABELS, ctx.view(process.getDataObjectLabelSet()));
        jsonObject.add(REQUEST_LABELS, ctx.view(process.getRequestLabelSet()));
        return jsonObject;
    }
}
