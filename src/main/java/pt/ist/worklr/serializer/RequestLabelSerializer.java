package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.RequestLabel;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RequestLabelSerializer implements JsonSerializer<RequestLabel> {

    @Override
    public JsonElement serialize(RequestLabel requestLabel, Type type, JsonSerializationContext ctx) {
	return new JsonPrimitive(requestLabel.getValue());
    }

}
