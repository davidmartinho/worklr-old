package pt.ist.worklr.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import pt.ist.worklr.domain.Worklr;

@Path("initDB")
public class InitDB {

	@GET
	public Response initDB() {
		Worklr.getInstance().init();
		return Response.ok().entity("DB Init Success").build();
	}

}
