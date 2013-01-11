package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.Role;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RoleSerializer implements JsonSerializer<Role> {

    public JsonElement serialize(Role role, Type type, JsonSerializationContext ctx) {
	JsonObject jsonObject = new JsonObject();

	jsonObject.addProperty("id", role.getExternalId());
	jsonObject.addProperty("name", role.getRoleName());

	return jsonObject;
    }

}
