package pt.ist.processpedia.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import pt.ist.processpedia.domain.Processpedia;
import pt.ist.processpedia.domain.Process;
import pt.ist.processpedia.domain.Request;
import pt.ist.processpedia.domain.User  ;
import pt.ist.processpedia.dto.ProcessDto;
import pt.ist.processpedia.dto.RequestDto;
import pt.ist.processpedia.dto.CreateProcessDto;
import pt.ist.processpedia.dto.CreateRequestDto;
import pt.ist.processpedia.dto.ClaimRequestDto;
import pt.ist.processpedia.dto.RespondRequestDto;
import pt.ist.processpedia.dto.mapper.DtoMapper;
import pt.ist.processpedia.service.ServiceManager;

@Path("/request")
public class RequestResource extends ProcesspediaResource {

    @GET
    @Path("{externalId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestDto getRequest(@PathParam("externalId") String externalId) {
        Request request = Processpedia.fromExternalId(externalId);
        return DtoMapper.requestDtoFromRequest(request);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RequestDto createRequest(CreateRequestDto createRequestDto) {
        User author = getAuthor();
        Request newRequest = ServiceManager.createRequest(createRequestDto, author);
        return DtoMapper.requestDtoFromRequest(newRequest);
    }
    
    @POST
    @Path("{externalId}/claim")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RequestDto getRequest(@PathParam("externalId") String externalId, ClaimRequestDto claimRequestDto) {
        User claimer = getAuthor();
        Request request = ServiceManager.claimRequest(claimRequestDto, claimer);
        return DtoMapper.requestDtoFromRequest(request);
    }
    
    @POST
    @Path("{externalId}/respond")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RequestDto getRequest(@PathParam("externalId") String externalId, RespondRequestDto respondRequestDto) {
        User responder = getAuthor();
        Request request = ServiceManager.respondRequest(respondRequestDto, responder);
        return DtoMapper.requestDtoFromRequest(request);
    }
    
}