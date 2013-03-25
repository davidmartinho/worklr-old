package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.domain.Role;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(Role.class)
public class RoleSerializer implements JsonViewer<Role> {

    public static final String ID = "id";
    public static final String NAME = "name";

    @Override
    public JsonElement view(Role role, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ID, role.getExternalId());
        jsonObject.addProperty(NAME, role.getRoleName());
        return jsonObject;
    }

}
