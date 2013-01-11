package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.DataObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DataObjectSerializer implements JsonSerializer<DataObject> {

    @Override
    public JsonElement serialize(DataObject dataObject, Type type, JsonSerializationContext ctx) {
	JsonObject jsonObject = new JsonObject();

	jsonObject.addProperty("id", dataObject.getExternalId());
	jsonObject.add("type", ctx.serialize(dataObject.getType()));
	jsonObject.add("label", ctx.serialize(dataObject.getLabel()));
	jsonObject.addProperty("value", dataObject.getValue());

	return jsonObject;
    }

}
