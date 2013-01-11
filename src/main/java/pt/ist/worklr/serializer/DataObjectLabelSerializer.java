package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.DataObjectLabel;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DataObjectLabelSerializer implements JsonSerializer<DataObjectLabel> {

    @Override
    public JsonElement serialize(DataObjectLabel label, Type type, JsonSerializationContext ctx) {
	return new JsonPrimitive(label.getValue());
    }

}
