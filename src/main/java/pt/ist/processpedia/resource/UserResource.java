package pt.ist.processpedia.resource;

import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import pt.ist.processpedia.domain.Processpedia;
import pt.ist.processpedia.domain.User;
import pt.ist.processpedia.domain.Tag;
import pt.ist.processpedia.dto.UserDto;
import pt.ist.processpedia.dto.TagDto;
import pt.ist.processpedia.dto.CreateUserDto;
import pt.ist.processpedia.dto.UpdateUserDto;
import pt.ist.processpedia.dto.mapper.DtoMapper;
import pt.ist.processpedia.service.ServiceManager;

@Path("/user")
public class UserResource extends ProcesspediaResource {
    
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getUser(@PathParam("userId") String userId) {
        User user = ServiceManager.getUser(userId);
        return DtoMapper.userDtoFromUser(user);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto createUser(CreateUserDto createUserDto) {
        User newUser = ServiceManager.createUser(createUserDto);
        return DtoMapper.userDtoFromUser(newUser);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto updateUser(UpdateUserDto updateUserDto) {
        User user = ServiceManager.updateUser(updateUserDto);
        return DtoMapper.userDtoFromUser(user);
    }
    
    @GET
    @Path("{userId}/tags")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<TagDto> getUserTagDtoSet(@PathParam("userId") String userId) {
        User user = ServiceManager.getUser(userId);
        Set<Tag> tagSet = user.getTagSet();
        return DtoMapper.tagDtoSetFromTagSet(tagSet);
    }
}