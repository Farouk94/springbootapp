package ws.springframework.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ws.springframework.domain.Group;

import java.util.Collection;
import java.util.stream.Stream;

public interface GroupRepository extends CrudRepository<Group, Integer> {
    public Group findByName(String name);

    public void deleteByName(String name);

    @Query(value = "SELECT name  ,COUNT (group_members_id) FROM group_users JOIN groups ON group_users.group_id=groups.id GROUP BY name", nativeQuery = true)
    public Stream<Object> getGroupNamesAndCount();

    public Collection<Group> findByAdminEmail(String adresse);

}