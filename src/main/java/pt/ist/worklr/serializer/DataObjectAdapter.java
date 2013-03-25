package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonAdapter;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.worklr.domain.DataObject;
import pt.ist.worklr.domain.DataObjectType;
import pt.ist.worklr.domain.Request;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.resource.WorklrResource;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(DataObject.class)
public class DataObjectAdapter implements JsonAdapter<DataObject> {

    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String LABEL = "label";
    public static final String VALUE = "value";

    public static final String AUTHOR_ID = "authorId";
    public static final String REQUEST_ID = "requestId";

    @Override
    public JsonElement view(DataObject dataObject, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ID, dataObject.getExternalId());
        jsonObject.add(TYPE, ctx.view(dataObject.getType()));
        jsonObject.add(LABEL, ctx.view(dataObject.getLabel()));
        jsonObject.addProperty(VALUE, dataObject.getValue());
        return jsonObject;
    }

    @Override
    public DataObject create(JsonElement jsonElement, JsonBuilder ctx) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        DataObjectType type = DataObjectType.fromString(jsonObject.get(TYPE).getAsString());
        String label = jsonObject.get(LABEL).getAsString();
        String value = jsonObject.get(VALUE).getAsString();
        String requestId = jsonObject.get(REQUEST_ID).getAsString();
        Request requestCreationContext = WorklrResource.readDomainObject(requestId);
        String authorId = jsonObject.get(AUTHOR_ID).getAsString();
        User author = WorklrResource.readDomainObject(authorId);
        DataObject dataObject = new DataObject(type, label, requestCreationContext, author, value);
        return dataObject;
    }

    @Override
    public DataObject update(JsonElement jsonElement, DataObject dataObject, JsonBuilder ctx) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String authorId = jsonObject.get(AUTHOR_ID).getAsString();
        User author = WorklrResource.readDomainObject(authorId);
        String value = jsonObject.get(VALUE).getAsString();
        dataObject.updateValue(value, author);
        return dataObject;
    }

}
