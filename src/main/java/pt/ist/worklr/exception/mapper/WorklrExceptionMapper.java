package pt.ist.worklr.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import pt.ist.worklr.exception.WorklrException;

import com.google.gson.JsonObject;

@Provider
public class WorklrExceptionMapper implements ExceptionMapper<WorklrException> {

    public Response toResponse(WorklrException exception) {
	JsonObject jsonObject = new JsonObject();
	jsonObject.addProperty("errorCode", exception.getErrorCode());
	jsonObject.addProperty("message", exception.getMessage());
	return Response.status(exception.getResponseStatus()).entity(jsonObject.toString()).type(MediaType.APPLICATION_JSON).build();
    }

}
