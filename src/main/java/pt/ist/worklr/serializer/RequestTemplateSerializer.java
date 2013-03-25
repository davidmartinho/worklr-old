package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.domain.RequestTemplate;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(RequestTemplate.class)
public class RequestTemplateSerializer implements JsonViewer<RequestTemplate> {

    public static final String ID = "id";
    public static final String INPUT_DATA_OBJECT_LABELS = "inputDataObjectLabels";
    public static final String CREATED_DATA_OBJECT_LABELS = "createdDataObjectLabels";
    public static final String RESPONSE_DATA_OBJECT_LABELS = "responseDataObjectLabels";
    public static final String POSSIBLE_SUBJECTS = "possibleSubjects";
    public static final String QUEUES = "queues";

    @Override
    public JsonElement view(RequestTemplate requestTemplate, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ID, requestTemplate.getExternalId());
        jsonObject.add(POSSIBLE_SUBJECTS, ctx.view(requestTemplate.getPossibleSubjectSet()));
        jsonObject.add(INPUT_DATA_OBJECT_LABELS, ctx.view(requestTemplate.getInputDataObjectLabelSet()));
        jsonObject.add(CREATED_DATA_OBJECT_LABELS, ctx.view(requestTemplate.getCreatedDataObjectLabelSet()));
        jsonObject.add(RESPONSE_DATA_OBJECT_LABELS, ctx.view(requestTemplate.getResponseDataObjectLabelSet()));
        jsonObject.add(QUEUES, ctx.view(requestTemplate.getExecutorQueueContextSet()));
        return jsonObject;
    }
}
