package ws.springframework.controllers;

import ws.springframework.domain.Comment;
import ws.springframework.domain.Group;
import ws.springframework.domain.User;
import ws.springframework.exceptions.RegistredException;
import ws.springframework.forms.GroupForm;
import ws.springframework.repositories.CommentsRepository;
import ws.springframework.services.GroupService;
import ws.springframework.services.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by farou_000 on 29/10/2016.
 */


@RestController
public class UserRestController {


    private UserService userService;
    private GroupService groupService;
    private CommentsRepository commentsRepository ;

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

    //user changes his/her full name
    @RequestMapping(value = "json/user/edit/fullName/{firstName}/{lastName}")
    public User editFullName(HttpServletRequest httpServletRequest, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString())
                .setFirstName(firstName);
        userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString())
                .setLastName(lastName);
        userService.saveUser(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));

        return userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString());

    }


    // user changes his/her biography
    @RequestMapping(value = "json/user/edit/biography/{biography}")
    public User editBiography(HttpServletRequest httpServletRequest, @RequestParam("biography") String biography) {
        userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString())
                .setBiography(biography);

        userService.saveUser(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));

        return userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString());

    }

    //user deletes his/her account
    @RequestMapping(value = "json/user/delete", method = RequestMethod.DELETE)
    public ResponseEntity delete(HttpServletRequest httpServletRequest) {
        for (Group g : groupService.listAllGroups()) {
            if (g.getGroupMembers().contains(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString())))
                g.getGroupMembers().remove(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        }
        userService.deleteUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString());

        return new ResponseEntity(HttpStatus.OK);
    }

    //user creates new group

    @RequestMapping(value = "json/user/new/group", method = RequestMethod.POST)
    public ResponseEntity<Group> saveGroup(@RequestBody GroupForm form) {
        Group group = new Group();
        group.setName(form.getName());
        group.setAdminEmail(form.getAdminName());
        group.setDescription(form.getDescription());
        groupService.saveGroup(group);

        return new ResponseEntity<>(groupService.getGroupByName(group.getName()), HttpStatus.CREATED);

    }

    //edit description of a group he owns
    @RequestMapping(value = "json/user/group/edit/{name}/{description}")
    public Group editMyGroupDescription(HttpServletRequest httpServletRequest,
                                        @RequestParam("name") String name, @RequestParam("description") String description) {
        if (groupService.getGroupByName(name).getAdminEmail().
                equals(userService.getLoggedUser(httpServletRequest).get("username").toString())) {
            groupService.getGroupByName(name).setDescription(description);
            groupService.saveGroup(groupService.getGroupByName(name));

        }
        return groupService.getGroupByName(name);
    }
    //user view a list of groups and membership count

    @RequestMapping(value = "json/user/groups", method = RequestMethod.GET)
    public ResponseEntity getAllGroupNamesAndCount() {

        return new ResponseEntity<>(groupService.getGroupNamesAndCount(), HttpStatus.OK);
    }

    //user joins group

    @RequestMapping(value = "json/user/group/join/{name}", method = RequestMethod.GET)
    public Group joinGroup(HttpServletRequest httpServletRequest, @RequestParam("name") String name) {

        if (groupService.getGroupByName(name).getGroupMembers().contains(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).
                get("username").toString()))) {
            throw new RegistredException("Already Registred");
        } else
            groupService.addUserToGroup(groupService.getGroupByName(name), userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).
                    get("username").toString()));

        return groupService.getGroupByName(name);
    }

    //user views members of the group he/she owns or has joined
    @RequestMapping(value = "json/user/group/view/{name}", method = RequestMethod.GET)
    public Collection<User> viewMyGroupMembers(@RequestParam("name") String name) {
        return groupService.getGroupByName(name).getGroupMembers();
    }

    //user comments on the dashbord of the group



    //user leaves the group
    @RequestMapping(value = "json/user/group/leave/{name}", method = RequestMethod.GET)
    public Collection<User> leaveGroup(HttpServletRequest httpServletRequest, @RequestParam("name") String name) {
        if (!groupService.getGroupByName(name).getGroupMembers().
                contains(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).
                        get("username").toString()))) {
            throw new RegistredException("User is not in this group");
        } else
            groupService.removeUserFromGroup(groupService.getGroupByName(name), userService.getUserByEmail(userService
                    .getLoggedUser(httpServletRequest).get("username").toString()));
        return groupService.getGroupByName(name).getGroupMembers();

    }


    //user check the profile of other users
    @RequestMapping(value = "json/user/{firstName}/{lastName}", method = RequestMethod.GET)
    public Collection<User> getUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return userService.findByFirstNameAndLastName(firstName, lastName);
    }

    // user comments on the dashboard of the group
    @RequestMapping(value = "json/comment/{groupName}/{comment}", method = RequestMethod.GET)
    public Collection<Comment> commentDashBoard(@RequestParam("groupName") String groupName, @RequestParam("comment") String comment , HttpServletRequest httpServletRequest) {



        String email = userService.getLoggedUser(httpServletRequest).get("username").toString() ;
        Comment comment1 = new Comment();

        comment1.setComment(comment);
        comment1.setOwnerfirstName(userService.getFNofCommentOwner(email));
        comment1.setOwnerLastName(userService.getLNofCommentOwner(email));
        commentsRepository.save(comment1) ;



        userService.addCommentToUser((userService.getUserByEmail(email)),comment1);

        groupService.addCommentToDashboard(groupService.getGroupByName(groupName) , comment1);
                return  groupService.getGroupByName(groupName).getDiscutionBoard();
    }


}
