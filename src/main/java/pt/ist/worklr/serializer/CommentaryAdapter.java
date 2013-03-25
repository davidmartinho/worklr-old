package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonCreator;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.domain.Commentary;
import pt.ist.worklr.domain.Request;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.resource.WorklrResource;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(Commentary.class)
public class CommentaryAdapter implements JsonCreator<Commentary>, JsonViewer<Commentary> {

    public static final String ID = "id";
    public static final String AUTHOR_ID = "authorId";
    public static final String REQUEST_ID = "requestId";
    public static final String TEXT = "text";
    public static final String AUTHOR = "author";
    public static final String TIMESTAMP = "timestamp";
    
    @Override
    public Commentary create(JsonElement jsonElement, JsonBuilder ctx) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String authorId = jsonObject.get(AUTHOR_ID).getAsString();
        String requestId = jsonObject.get(REQUEST_ID).getAsString();
        String text = jsonObject.get(TEXT).getAsString();
        User author = WorklrResource.readDomainObject(authorId);
        Request request = WorklrResource.readDomainObject(requestId);
        Commentary commentary = new Commentary(text, author, request);
        return commentary;
    }

    @Override
    public JsonElement view(Commentary commentary, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ID, commentary.getExternalId());
        jsonObject.addProperty(TEXT, commentary.getText());
        jsonObject.add(AUTHOR, ctx.view(commentary.getAuthor()));
        jsonObject.add(TIMESTAMP, ctx.view(commentary.getTimestamp()));
        return jsonObject;
    }

}
