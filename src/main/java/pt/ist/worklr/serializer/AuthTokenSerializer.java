package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.AuthToken;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AuthTokenSerializer implements JsonSerializer<AuthToken> {

    @Override
    public JsonElement serialize(AuthToken authToken, Type type, JsonSerializationContext ctx) {
	JsonObject jsonObject = new JsonObject();
	jsonObject.addProperty("userId", authToken.getUser().getOid());
	jsonObject.addProperty("token", authToken.getToken());
	return jsonObject;
    }

}
