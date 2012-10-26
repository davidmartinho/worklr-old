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
import pt.ist.processpedia.domain.User;
import pt.ist.processpedia.domain.Label;
import pt.ist.processpedia.dto.LabelDto;
import pt.ist.processpedia.dto.CreateLabelDto;
import pt.ist.processpedia.dto.mapper.DtoMapper;
import pt.ist.processpedia.service.ServiceManager;

@Path("/label")
public class LabelResource extends ProcesspediaResource {
    
    @GET
    @Path("{externalId}")
    @Produces(MediaType.APPLICATION_JSON)
    public LabelDto getLabel(@PathParam("externalId") String externalId) {
        Label label = ServiceManager.getLabel(externalId);
        return DtoMapper.labelDtoFromLabel(label);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LabelDto createLabel(CreateLabelDto createLabelDto) {
        Label newLabel = ServiceManager.createLabel(createLabelDto, getAuthor());
        return DtoMapper.labelDtoFromLabel(newLabel);
    }
}