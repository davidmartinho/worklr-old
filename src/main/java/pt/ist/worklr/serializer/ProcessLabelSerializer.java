package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.domain.ProcessLabel;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

@DefaultJsonAdapter(ProcessLabel.class)
public class ProcessLabelSerializer implements JsonViewer<ProcessLabel> {

    @Override
    public JsonElement view(ProcessLabel label, JsonBuilder ctx) {
        return new JsonPrimitive(label.getValue());
    }

}
