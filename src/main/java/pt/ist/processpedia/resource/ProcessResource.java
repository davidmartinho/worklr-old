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
import pt.ist.processpedia.domain.User  ;
import pt.ist.processpedia.dto.ProcessDto;
import pt.ist.processpedia.dto.UserDto;
import pt.ist.processpedia.dto.CreateProcessDto;
import pt.ist.processpedia.dto.mapper.DtoMapper;
import pt.ist.processpedia.service.ServiceManager;

@Path("/process")
public class ProcessResource extends ProcesspediaResource {

    @GET
    @Path("{processId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProcessDto getProcess(@PathParam("processId") String processId) {
        Process process = ServiceManager.getProcess(processId);
        return DtoMapper.processDtoFromProcess(process);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ProcessDto createProcess(CreateProcessDto createProcessDto) {
        User initiator = getAuthor();
        Process newProcess = ServiceManager.createProcess(createProcessDto, initiator);
        return DtoMapper.processDtoFromProcess(newProcess);
    }
    
    @GET
    @Path("{processId}/initiator")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getProcessInitiator(@PathParam("processId") String processId) {
        Process process = ServiceManager.getProcess(processId);
        User initiator = process.getInitiator();
        return DtoMapper.userDtoFromUser(initiator);
    }
    
    @GET
    @Path("{processId}/participants")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<UserDto> getProcessParticipants(@PathParam("processId") String processId) {
        Process process = ServiceManager.getProcess(processId);
        Set<User> participants = process.getParticipants();
        return DtoMapper.userDtoSetFromUserSet(participants);
    }
}