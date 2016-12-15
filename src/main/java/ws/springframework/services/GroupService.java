package ws.springframework.services;

import ws.springframework.domain.Comment;
import ws.springframework.domain.Group;
import ws.springframework.domain.User;

import java.util.Collection;
import java.util.stream.Stream;


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

    public Collection<Group> getGroupByAdminEmail(String email);

    public Comment getCommentById(Integer id);

}
