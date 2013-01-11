package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.exception.WorklrException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class WorklrExceptionSerializer implements JsonSerializer<WorklrException> {

    public JsonElement serialize(WorklrException exception, Type type, JsonSerializationContext ctx) {
	return new JsonObject();
    }

}
