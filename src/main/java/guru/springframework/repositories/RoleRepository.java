package guru.springframework.repositories;

import guru.springframework.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by farou_000 on 31/10/2016.
 */
public interface RoleRepository extends CrudRepository<Role, String> {

}
