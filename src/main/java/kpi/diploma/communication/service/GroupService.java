package kpi.diploma.communication.service;

import kpi.diploma.communication.data.GroupRepository;
import kpi.diploma.communication.data.UserGroupRepository;
import kpi.diploma.communication.dto.GroupDTO;
import kpi.diploma.communication.dto.GroupDTO;
import kpi.diploma.communication.model.Group;
import kpi.diploma.communication.model.Group;
import kpi.diploma.communication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupRepository groupRepository;

    public List<GroupDTO> getAllGroups(){
        List<Group> groups = (List<Group>) groupRepository.findAll();
        return parseGroupListToDTO(groups);
    }
    public List<GroupDTO> getGroupsForTeacher(String teacherEmail){
        List<Group> groups = userGroupRepository.findGroupsByUserEmail(teacherEmail);
        User user = userService.getUserById(teacherEmail);
        if(user.getGroup()!=null){
            groups.add(user.getGroup());
        }
        return parseGroupListToDTO(groups);
    }

    public GroupDTO getMyGroup(String teacherEmail){
        User user = userService.getUserById(teacherEmail);
        return user.getGroup()!=null?parsingGroupDTO(user.getGroup()):null;
    }

    private List<GroupDTO> parseGroupListToDTO(List<Group> Groups) {
        return Groups.stream()
                .map(this::parsingGroupDTO)
                .collect(Collectors.toList());
    }


    private GroupDTO parsingGroupDTO(Group group) {
        return GroupDTO.builder()
                .id(group.getId())
                .title(group.getTitle())
                .course(group.getCourse())
                .speciality(group.getSpeciality())
                .build();
    }
}
