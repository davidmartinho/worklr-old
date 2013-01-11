package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.Queue;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class QueueSerializer implements JsonSerializer<Queue> {

    @Override
    public JsonElement serialize(Queue queue, Type type, JsonSerializationContext ctx) {
	JsonObject jsonObject = new JsonObject();

	jsonObject.addProperty("id", queue.getExternalId());
	jsonObject.addProperty("name", queue.getName());

	return jsonObject;
    }

}
