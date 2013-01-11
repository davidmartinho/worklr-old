package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.Request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RequestSerializer implements JsonSerializer<Request> {

    @Override
    public JsonElement serialize(Request request, Type type, JsonSerializationContext ctx) {
	JsonObject jsonObject = new JsonObject();

	jsonObject.addProperty("id", request.getExternalId());
	jsonObject.add("subject", ctx.serialize(request.getSubject()));
	jsonObject.add("initiator", ctx.serialize(request.getInitiator()));
	jsonObject.add("creationTimestamp", ctx.serialize(request.getCreationTimestamp()));

	return jsonObject;
    }
}
