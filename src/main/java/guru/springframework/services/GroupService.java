package guru.springframework.services;

import guru.springframework.domain.Comment;
import guru.springframework.domain.Group;
import guru.springframework.domain.User;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Created by farou_000 on 29/10/2016.
 */
public interface GroupService {
    Collection<Group> listAllGroups();

    Group getGroupByName(String name);

    Group getGroupById(Integer id);

    Group saveGroup(Group group);

    void deleteGroup(String name);

    Boolean ifExist(String name);

    void addUserToGroup(Group group, User user);

    public Stream<Object> getGroupNamesAndCount();

    public void removeUserFromGroup(Group group, User user);

    public void addCommentToDashboard(Group group, Comment comment);

    public Comment getCommentByName(String name);

    public Collection<Group> getGroupByAdminEmail(String email) ;

}
