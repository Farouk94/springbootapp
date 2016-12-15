package ws.springframework.repositories;

import org.springframework.data.repository.CrudRepository;
import ws.springframework.domain.Role;


public interface RoleRepository extends CrudRepository<Role, String> {

}
