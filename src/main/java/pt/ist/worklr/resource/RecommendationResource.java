package pt.ist.worklr.resource;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import pt.ist.worklr.domain.Recommendation;
import pt.ist.worklr.domain.Request;
import pt.ist.worklr.domain.User;

@Path("/request")
public class RecommendationResource extends WorklrResource {

    @Path("{id}/recommendations")
    public Response getRecommendationsFor(@PathParam("id")
    String externalId) {
	User user = getRequestAuthor();
	Request request = readDomainObject(externalId);
	List<Recommendation> recommendationList = request.getRecommendationsForUser(user);
	return Response.ok().entity(loadJsonStringForObject(recommendationList)).build();
    }
}
