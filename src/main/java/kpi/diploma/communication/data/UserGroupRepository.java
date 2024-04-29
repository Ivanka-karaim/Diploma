package kpi.diploma.communication.data;

import kpi.diploma.communication.model.Group;
import kpi.diploma.communication.model.Role;
import kpi.diploma.communication.model.User;
import kpi.diploma.communication.model.UserGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserGroupRepository extends CrudRepository<UserGroup, String> {
    @Query("SELECT ug.user FROM UserGroup ug WHERE ug.group.title = :groupTitle AND :role MEMBER OF ug.user.roles")
    List<User> findUsersByGroupTitle(@Param("groupTitle") String groupTitle, @Param("role") Role role);
    @Query("SELECT ug.group FROM UserGroup ug WHERE ug.user.email = :teacherEmail")
    List<Group> findGroupsByUserEmail(@Param("teacherEmail") String teacherEmail);
}
