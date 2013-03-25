package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.domain.Queue;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(Queue.class)
public class QueueSerializer implements JsonViewer<Queue> {

    public static final String ID = "id";
    public static final String NAME = "name";

    @Override
    public JsonElement view(Queue queue, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ID, queue.getExternalId());
        jsonObject.addProperty(NAME, queue.getName());
        return jsonObject;
    }
}
