package ws.springframework.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ws.springframework.domain.Comment;
import ws.springframework.domain.Group;
import ws.springframework.domain.Role;
import ws.springframework.domain.User;
import ws.springframework.repositories.CommentsRepository;
import ws.springframework.repositories.GroupRepository;
import ws.springframework.repositories.RoleRepository;
import ws.springframework.repositories.UserRepository;
import ws.springframework.services.GroupService;
import ws.springframework.services.UserService;



@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent> {
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CommentsRepository commentsRepository;
    private GroupService groupService;
    private UserService userService;
    private Logger log = Logger.getLogger(Loader.class);

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setCommentsRepository(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


        //groups

        Group group = new Group();
        group.setName("Manchester United Lovers");
        group.setAdminEmail("farouk.ennia@telecom-st-etienne.fr");
        group.setDescription("WE LOVE MAN U");
        log.info("Saved group - id: " + group.getName());
        groupRepository.save(group);

        Group group1 = new Group();
        group1.setName("Manchester City Lovers");
        group1.setAdminEmail("Alaeddine.elAssad@telecom-st-etienne.fr");
        group1.setDescription("WE LOVE MAN City");

        log.info("Saved group - id: " + group1.getName());
        groupRepository.save(group1);

        Group group2 = new Group();
        group2.setName("Atletico Madrid Lovers");
        group2.setAdminEmail("farouk.ennia@telecom-st-etienne.fr");
        group2.setDescription("WE LOVE Atletico");
        log.info("Saved group - id: " + group2.getName());
        groupRepository.save(group2);

        Group group3 = new Group();
        group3.setName("Fc Barcelona Lovers");
        group3.setAdminEmail("yassir.merhder@telecom-st-etienne.fr");
        group3.setDescription("WE LOVE Barcelona");
        log.info("Saved group - id: " + group3.getName());
        groupRepository.save(group3);

        //Roles

        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);
        log.info("Saved role - id: " + role.getName());

        Role role1 = new Role();
        role1.setName("USER");
        roleRepository.save(role1);
        log.info("Saved role - id: " + role1.getName());

        //Users

        User user1 = new User();
        user1.setEmailAdresse("farouk.ennia@telecom-st-etienne.fr");
        user1.setLastName("Ennia");
        user1.setFirstName("Farouk");
        user1.setBiography("Hey im 22 years old");
        user1.setPassword("password");
        user1.setEnabled(true);
        userRepository.save(user1);
        log.info("Saved user- email: " + user1.getEmailAdresse());


        User user2 = new User();
        user2.setEmailAdresse("Alaeddine.elAssad@telecom-st-etienne.fr");
        user2.setLastName("Alae");
        user2.setFirstName("El Assad");
        user2.setBiography("Hey im 23 years old");
        user2.setPassword("password");
        user2.setEnabled(true);
        userRepository.save(user2);
        log.info("Saved user- email: " + user2.getEmailAdresse());

        User user3 = new User();
        user3.setEmailAdresse("yassir.merhder@telecom-st-etienne.fr");
        user3.setLastName("Yassir");
        user3.setFirstName("Merhder");
        user3.setBiography("Hey im 23 years old");
        user3.setPassword("password");
        user3.setEnabled(true);
        userRepository.save(user3);
        log.info("Saved user- email: " + user3.getEmailAdresse());


        //Comments
        Comment comment = new Comment();
        comment.setComment(" Heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeey");
        log.info("Saved comment- id: " + comment.getComment());
        commentsRepository.save(comment);
        Comment comment1 = new Comment();
        comment1.setComment("I love this group");
        log.info("Saved comment- id: " + comment1.getComment());
        commentsRepository.save(comment1);


        log.info("add user to group ");
        groupService.addUserToGroup(group, user1);
        groupService.addUserToGroup(group2, user1);

        groupService.addUserToGroup(group1, user2);

        groupService.addUserToGroup(group3, user3);


        log.info("add role to user");
        userService.addRoleToUser(user1, role);
        userService.addRoleToUser(user2, role);
        userService.addRoleToUser(user3, role);


        log.info("add comment to user");
        userService.addCommentToUser(user1, comment);
        userService.addCommentToUser(user1, comment1);


        log.info("add comment to group");
        groupService.addCommentToDashboard(group, comment);
        groupService.addCommentToDashboard(group, comment1);


    }
}
