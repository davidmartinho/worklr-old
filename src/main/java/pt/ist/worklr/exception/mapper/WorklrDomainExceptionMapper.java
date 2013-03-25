package pt.ist.worklr.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import pt.ist.worklr.domain.exception.WorklrDomainException;

import com.google.gson.JsonObject;

@Provider
public class WorklrDomainExceptionMapper implements ExceptionMapper<WorklrDomainException> {

    private static final String MESSAGE = "message";
    
    @Override
    public Response toResponse(WorklrDomainException exception) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(MESSAGE, exception.getMessage());
        return Response.status(exception.getResponseStatus()).entity(jsonObject.toString()).type(MediaType.APPLICATION_JSON)
                .build();
    }

}
