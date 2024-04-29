package kpi.diploma.communication.service;

import kpi.diploma.communication.data.UserGroupRepository;
import kpi.diploma.communication.dto.GroupDTO;
import kpi.diploma.communication.dto.GroupDTO;
import kpi.diploma.communication.model.Group;
import kpi.diploma.communication.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;
    public List<GroupDTO> getGroupsForTeacher(String teacherEmail){
        List<Group> groups = userGroupRepository.findGroupsByUserEmail(teacherEmail);
        return parseGroupListToDTO(groups);
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
