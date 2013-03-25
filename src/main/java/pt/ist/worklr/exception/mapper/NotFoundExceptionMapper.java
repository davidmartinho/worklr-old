package pt.ist.worklr.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.gson.JsonObject;
import com.sun.jersey.api.NotFoundException;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	@Override
	public Response toResponse(NotFoundException exception) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("message", "The resource you tried to reach was not found");
		return Response.status(Status.NOT_FOUND).entity(jsonObject.toString()).type(MediaType.APPLICATION_JSON).build();
	}

}