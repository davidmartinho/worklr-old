package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.domain.RequestLabel;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

@DefaultJsonAdapter(RequestLabel.class)
public class RequestLabelSerializer implements JsonViewer<RequestLabel> {

    @Override
    public JsonElement view(RequestLabel requestLabel, JsonBuilder ctx) {
        return new JsonPrimitive(requestLabel.getValue());
    }

}
