package pt.ist.worklr.serializer;

import java.lang.reflect.Type;

import pt.ist.worklr.domain.Recommendation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RecommendationSerializer implements JsonSerializer<Recommendation> {

    @Override
    public JsonElement serialize(Recommendation recommendation, Type type, JsonSerializationContext ctx) {
	JsonObject jsonObject = new JsonObject();

	jsonObject.add("possibleSubjects", ctx.serialize(recommendation.getPossibleSubjects()));
	jsonObject.add("inputDataObjectLabels", ctx.serialize(recommendation.getInputDataObjectLabels()));
	jsonObject.add("creationDataObjectLabels", ctx.serialize(recommendation.getCreationDataObjectLabels()));
	jsonObject.add("responseDataObjectLabels", ctx.serialize(recommendation.getResponseDataObjectLabels()));

	return jsonObject;
    }
}
