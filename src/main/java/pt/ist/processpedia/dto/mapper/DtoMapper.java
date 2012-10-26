package pt.ist.processpedia.dto.mapper;

import java.util.Set;
import java.util.HashSet;
import pt.ist.processpedia.domain.Process;
import pt.ist.processpedia.domain.Request;
import pt.ist.processpedia.domain.User;
import pt.ist.processpedia.domain.Label;
import pt.ist.processpedia.domain.Tag;
import pt.ist.processpedia.dto.UserDto;
import pt.ist.processpedia.dto.ProcessDto;
import pt.ist.processpedia.dto.RequestDto;
import pt.ist.processpedia.dto.LabelDto;
import pt.ist.processpedia.dto.TagDto;
import pt.ist.processpedia.dto.ProcessTagDto;
import pt.ist.processpedia.dto.RequestTagDto;


public class DtoMapper {

    public static UserDto userDtoFromUser(User user) {
        return new UserDto(user.getExternalId(), user.getEmail());
    }
    
    public static Set<UserDto> userDtoSetFromUserSet(Set<User> userSet) {
        Set<UserDto> userDtoSet = new HashSet<UserDto>();
        for(User user : userSet) {
            userDtoSet.add(userDtoFromUser(user));
        }
        return userDtoSet;
    }    
    public static ProcessDto processDtoFromProcess(Process process) {
        return new ProcessDto(process.getExternalId(), process.getTitle());
    }
    
    public static RequestDto requestDtoFromRequest(Request request) {
        return new RequestDto(request.getExternalId(), request.getSubject(), processDtoFromProcess(request.getProcess()), userDtoFromUser(request.getInitiator()));
    }
    
    public static Set<RequestDto> requestDtoSetFromRequestSet(Set<Request> requestSet) {
        Set<RequestDto> requestDtoSet = new HashSet<RequestDto>();
        for(Request request : requestSet) {
            requestDtoSet.add(requestDtoFromRequest(request));
        }
        return requestDtoSet;
    }
    
    public static LabelDto labelDtoFromLabel(Label label) {
        return new LabelDto(label.getExternalId(), label.getLabel());
    }
    
    public static TagDto tagDtoFromTag(Tag tag) {
        if(tag.hasRequest()) {
            return new RequestTagDto(tag.getExternalId(), labelDtoFromLabel(tag.getLabel()), requestDtoFromRequest(tag.getRequest()));
        } else if(tag.hasProcess()) {
            return new ProcessTagDto(tag.getExternalId(), labelDtoFromLabel(tag.getLabel()), processDtoFromProcess(tag.getProcess()));
        } else {
            return null;
        }
    }
    
    public static Set<TagDto> tagDtoSetFromTagSet(Set<Tag> tagSet) {
        Set<TagDto> tagDtoSet = new HashSet<TagDto>();
        for(Tag tag : tagSet) {
            tagDtoSet.add(tagDtoFromTag(tag));
        }
        return tagDtoSet;
    }
    
}