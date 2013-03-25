package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.domain.DataObjectType;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

@DefaultJsonAdapter(DataObjectType.class)
public class DataObjectTypeSerializer implements JsonViewer<DataObjectType> {

    @Override
    public JsonElement view(DataObjectType dataObjectType, JsonBuilder ctx) {
        return new JsonPrimitive(dataObjectType.getType());
    }

}
