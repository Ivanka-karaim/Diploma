package kpi.diploma.communication.data;

import kpi.diploma.communication.model.Role;
import kpi.diploma.communication.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    List<User> findByGroupTitleAndRolesContaining(String groupTitle, Role role);

    List<User> findByGroupTitle(String group);

    List<User> findByRolesContaining(Role role);


}
