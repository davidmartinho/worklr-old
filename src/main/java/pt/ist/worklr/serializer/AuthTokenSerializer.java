package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.domain.AuthToken;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(AuthToken.class)
public class AuthTokenSerializer implements JsonViewer<AuthToken> {

    public static final String USER_ID = "userId";
    public static final String TOKEN = "token";

    @Override
    public JsonElement view(AuthToken authToken, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(USER_ID, authToken.getUser().getOid());
        jsonObject.addProperty(TOKEN, authToken.getToken());
        return jsonObject;
    }
}
