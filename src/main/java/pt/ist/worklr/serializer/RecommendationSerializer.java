package pt.ist.worklr.serializer;

import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.worklr.domain.Recommendation;
import pt.ist.worklr.utils.DefaultJsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(Recommendation.class)
public class RecommendationSerializer implements JsonViewer<Recommendation> {

    public static final String ORDER = "order";

    @Override
    public JsonElement view(Recommendation recommendation, JsonBuilder ctx) {
        JsonObject jsonObject = ctx.view(recommendation.getRequestTemplate()).getAsJsonObject();
        jsonObject.addProperty(ORDER, recommendation.getOrder());
        return jsonObject;
    }
}
