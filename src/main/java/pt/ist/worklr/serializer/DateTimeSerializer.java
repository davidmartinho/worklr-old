package pt.ist.worklr.serializer;

import org.joda.time.DateTime;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

@DefaultJsonAdapter(DateTime.class)
public class DateTimeSerializer implements JsonViewer<DateTime> {

    @Override
    public JsonElement view(DateTime dateTime, JsonBuilder ctx) {
        return new JsonPrimitive(dateTime.toString());
    }
}
