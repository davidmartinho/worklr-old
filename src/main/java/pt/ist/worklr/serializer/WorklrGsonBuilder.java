package pt.ist.worklr.serializer;

import org.joda.time.DateTime;

import pt.ist.worklr.domain.AuthToken;
import pt.ist.worklr.domain.DataObjectLabel;
import pt.ist.worklr.domain.ProcessLabel;
import pt.ist.worklr.domain.Queue;
import pt.ist.worklr.domain.Request;
import pt.ist.worklr.domain.RequestLabel;
import pt.ist.worklr.domain.Role;
import pt.ist.worklr.domain.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class WorklrGsonBuilder {

    private static Gson gson;

    static {
	GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.serializeNulls();
	gsonBuilder.setPrettyPrinting();
	gsonBuilder.registerTypeAdapter(pt.ist.worklr.domain.Process.class, new ProcessSerializer());
	gsonBuilder.registerTypeAdapter(Request.class, new RequestSerializer());
	gsonBuilder.registerTypeAdapter(User.class, new UserSerializer());
	gsonBuilder.registerTypeAdapter(ProcessLabel.class, new ProcessLabelSerializer());
	gsonBuilder.registerTypeAdapter(RequestLabel.class, new RequestLabelSerializer());
	gsonBuilder.registerTypeAdapter(DataObjectLabel.class, new DataObjectLabelSerializer());
	gsonBuilder.registerTypeAdapter(Queue.class, new QueueSerializer());
	gsonBuilder.registerTypeAdapter(Role.class, new RoleSerializer());
	gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());
	gsonBuilder.registerTypeAdapter(AuthToken.class, new AuthTokenSerializer());

	// gsonBuilder.registerTypeHierarchyAdapter(Throwable.class, new
	// WorklrExceptionSerializer());
	gson = gsonBuilder.create();
    }

    public String build(Object obj) {
	return gson.toJson(obj);
    }

    public JsonElement buildJsonTree(Object obj) {
	return gson.toJsonTree(obj);
    }

    public JsonElement parseJson(String jsonData) {
	return new JsonParser().parse(jsonData);
    }

}
