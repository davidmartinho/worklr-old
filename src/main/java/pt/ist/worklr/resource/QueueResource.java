package pt.ist.worklr.resource;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.ist.worklr.domain.Queue;
import pt.ist.worklr.domain.Worklr;

@Path("queues")
public class QueueResource extends WorklrResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQueues() {
	    getRequestAuthorId();
		Set<Queue> queueSet = Worklr.getInstance().getQueueSet();
		return Response.ok(view(queueSet)).build();
	}

}
