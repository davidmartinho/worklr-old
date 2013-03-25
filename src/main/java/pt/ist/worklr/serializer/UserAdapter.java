package pt.ist.worklr.serializer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pt.ist.bennu.json.JsonAdapter;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.worklr.domain.Role;
import pt.ist.worklr.domain.User;
import pt.ist.worklr.resource.WorklrResource;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(User.class)
public class UserAdapter implements JsonAdapter<User> {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String AVATAR_URL = "avatarUrl";
    public static final String ROLES = "roles";
    public static final String ADMIN = "admin";

    @Override
    public User create(JsonElement json, JsonBuilder ctx) {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get(NAME).getAsString();
        String email = jsonObject.get(EMAIL).getAsString();
        String password = jsonObject.get(PASSWORD).getAsString();
        JsonArray jsonArray = jsonObject.get(ROLES).getAsJsonArray();
        Iterator<JsonElement> iterator = jsonArray.iterator();
        Set<Role> roleSet = new HashSet<Role>();
        while (iterator.hasNext()) {
            String roleExternalId = iterator.next().getAsString();
            Role role = WorklrResource.readDomainObject(roleExternalId);
            roleSet.add(role);
        }
        boolean isAdmin = jsonObject.get(ADMIN).getAsBoolean();
        User user = new User(name, email, password, roleSet, isAdmin);
        return user;
    }

    @Override
    public User update(JsonElement json, User user, JsonBuilder ctx) {
        user.setName(json.getAsJsonObject().get(NAME).getAsString());
        user.setEmail(json.getAsJsonObject().get(EMAIL).getAsString());
        return user;
    }

    @Override
    public JsonElement view(User user, JsonBuilder ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ID, user.getExternalId());
        jsonObject.addProperty(NAME, user.getName());
        jsonObject.addProperty(EMAIL, user.getEmail());
        jsonObject.addProperty(AVATAR_URL, user.getAvatarUrl());
        jsonObject.add(ROLES, ctx.view(user.getRoleSet()));
        jsonObject.addProperty(ADMIN, user.getAdmin());
        return jsonObject;
    }
}
