package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.ProcessTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ProcessTemplateSerializer implements JsonSerializer<ProcessTemplate> {

    @Override
    public JsonElement serialize(ProcessTemplate processTemplate, Type type, JsonSerializationContext ctx) {
	JsonObject jsonObject = new JsonObject();

	jsonObject.addProperty("id", processTemplate.getExternalId());
	jsonObject.add("processGoal", ctx.serialize(processTemplate.getProcessGoal()));
	jsonObject.add("requestTemplates", ctx.serialize(processTemplate.getRequestTemplateSet()));

	return jsonObject;
    }
}
