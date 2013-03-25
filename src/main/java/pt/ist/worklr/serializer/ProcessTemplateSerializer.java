package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.domain.ProcessTemplate;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(ProcessTemplate.class)
public class ProcessTemplateSerializer implements JsonViewer<ProcessTemplate> {

    public static final String ID = "id";
    public static final String PROCESS_GOAL = "processGoal";
    public static final String REQUEST_TEMPLATES = "requestTemplates";

    @Override
    public JsonElement view(ProcessTemplate processTemplate, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ID, processTemplate.getExternalId());
        jsonObject.add(PROCESS_GOAL, ctx.view(processTemplate.getProcessGoal()));
        jsonObject.add(REQUEST_TEMPLATES, ctx.view(processTemplate.getRequestTemplateSet()));
        return jsonObject;
    }
}
