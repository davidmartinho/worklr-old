package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTimeSerializer implements JsonSerializer<DateTime> {

    public JsonElement serialize(DateTime dateTime, Type type, JsonSerializationContext ctx) {
	return new JsonPrimitive(dateTime.toString());
    }
}
