package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.Process;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ProcessSerializer implements JsonSerializer<pt.ist.worklr.domain.Process> {

    public JsonElement serialize(Process process, Type type, JsonSerializationContext ctx) {
	JsonObject jsonObject = new JsonObject();

	jsonObject.addProperty("id", process.getExternalId());
	jsonObject.add("title", ctx.serialize(process.getTitle()));
	jsonObject.add("participants", ctx.serialize(process.getParticipants()));
	jsonObject.add("initiator", ctx.serialize(process.getInitiator()));

	return jsonObject;
    }

}
