package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.ProcessLabel;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ProcessLabelSerializer implements JsonSerializer<ProcessLabel> {

    @Override
    public JsonElement serialize(ProcessLabel label, Type type, JsonSerializationContext ctx) {
	return new JsonPrimitive(label.getValue());
    }

}
