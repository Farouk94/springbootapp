package ws.springframework.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ws.springframework.domain.Comment;
import ws.springframework.domain.Group;
import ws.springframework.domain.User;
import ws.springframework.exceptions.EntityNotFoundException;
import ws.springframework.repositories.CommentsRepository;
import ws.springframework.repositories.GroupRepository;
import ws.springframework.repositories.UserRepository;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Created by farou_000 on 29/10/2016.
 */

@Service
public class GroupServiceImp implements GroupService {
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private CommentsRepository commentsRepository;
    private Logger log = Logger.getLogger(GroupServiceImp.class);


    @Autowired
    public void setCommentsRepository(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Collection<Group> listAllGroups() {
        return (Collection<Group>) groupRepository.findAll();
    }

    @Override
    public Group getGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    @Override
    public Group getGroupById(Integer id) {
        return groupRepository.findOne(id);
    }

    @Override
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(String name) {
        groupRepository.deleteByName(name);
    }

    @Override
    public Boolean ifExist(String name) {
        return groupRepository.exists(groupRepository.findByName(name).getId());
    }

    @Override
    @Transactional
    public void addUserToGroup(Group group, User user) {
        log.info("Adding user " + user.getEmailAdresse() + "to group " + group.getName());
        User adding_user = userRepository.findOne(user.getId());
        if (adding_user == null) {
            throw new EntityNotFoundException("User");
        }

        Group adding_group = groupRepository.findByName(group.getName());
        if (adding_group == null) {
            throw new EntityNotFoundException("Group");
        }

        adding_group.getGroupMembers().add(adding_user);
        groupRepository.save(adding_group);

    }

    @Override
    @Transactional
    public void removeUserFromGroup(Group group, User user) {
        log.info("removing user " + user.getEmailAdresse() + "from group " + group.getName());
        User removing_user = userRepository.findOne(user.getId());
        if (removing_user == null) {
            throw new EntityNotFoundException("User");
        }

        Group removing_group = groupRepository.findByName(group.getName());
        if (removing_group == null) {
            throw new EntityNotFoundException("Group");
        }

        removing_group.getGroupMembers().remove(removing_user);
        groupRepository.save(removing_group);

    }

    @Override
    public Stream<Object> getGroupNamesAndCount() {
        return groupRepository.getGroupNamesAndCount();
    }

    @Override
    public void addCommentToDashboard(Group group, Comment comment) {
        log.info("adding comment" + comment.getComment() + " to group" + group.getName());
        Group adding_group = groupRepository.findByName(group.getName());
        if (adding_group == null) {
            throw new EntityNotFoundException("Group");
        }

        Comment adding_comment = commentsRepository.findOne(comment.getId());
        if (adding_comment == null) {
            throw new EntityNotFoundException("Comment");
        }
        adding_group.getDiscutionBoard().add(adding_comment);
        groupRepository.save(adding_group);


    }

    @Override
    public Comment getCommentByName(String name) {
        return commentsRepository.findByComment(name);
    }

    @Override
    public Collection<Group> getGroupByAdminEmail(String email) {

        return groupRepository.findByAdminEmail(email);

    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentsRepository.findOne(id);
    }
}
