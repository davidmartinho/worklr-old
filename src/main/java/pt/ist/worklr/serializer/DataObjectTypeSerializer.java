package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.DataObjectType;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DataObjectTypeSerializer implements JsonSerializer<DataObjectType> {

    @Override
    public JsonElement serialize(DataObjectType dataObjectType, Type type, JsonSerializationContext ctx) {
	return new JsonPrimitive(dataObjectType.getType());
    }

}
