package kpi.diploma.communication.data;

import kpi.diploma.communication.model.Chat;
import kpi.diploma.communication.model.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
