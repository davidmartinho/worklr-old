package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.RequestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RequestTemplateSerializer implements JsonSerializer<RequestTemplate> {

    @Override
    public JsonElement serialize(RequestTemplate requestTemplate, Type type, JsonSerializationContext ctx) {
	JsonObject jsonObject = new JsonObject();

	jsonObject.addProperty("id", requestTemplate.getExternalId());
	jsonObject.add("inputDataObjectLabels", ctx.serialize(requestTemplate.getInputDataObjectLabelSet()));
	jsonObject.add("createdDataObjectLabels", ctx.serialize(requestTemplate.getCreatedDataObjectLabelSet()));
	jsonObject.add("responseDataObjectLabels", ctx.serialize(requestTemplate.getResponseDataObjectLabelSet()));

	return null;
    }
}
