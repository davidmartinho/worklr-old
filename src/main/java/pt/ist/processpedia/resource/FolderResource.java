package pt.ist.processpedia.resource;

import java.util.Set;
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
import pt.ist.processpedia.dto.ClaimRequestDto;
import pt.ist.processpedia.dto.mapper.DtoMapper;
import pt.ist.processpedia.service.ServiceManager;

@Path("/")
public class FolderResource extends ProcesspediaResource {

    @GET
    @Path("inbox")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<RequestDto> getInboxRequests() {
        User author = getAuthor();
        Set<Request> inboxRequestSet = author.getInboxRequestSet();
        return DtoMapper.requestDtoSetFromRequestSet(inboxRequestSet);
    }

    @GET
    @Path("sent")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<RequestDto> getSentRequests() {
        User author = getAuthor();
        Set<Request> sentRequestSet = author.getSentRequestSet();
        return DtoMapper.requestDtoSetFromRequestSet(sentRequestSet);
    }
    
    @GET
    @Path("completed")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<RequestDto> getCompletedRequests() {
        User author = getAuthor();
        Set<Request> completedRequestSet = author.getCompletedRequestSet();
        return DtoMapper.requestDtoSetFromRequestSet(completedRequestSet);
    }
}