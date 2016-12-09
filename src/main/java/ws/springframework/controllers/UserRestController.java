package ws.springframework.controllers;

import ws.springframework.domain.Comment;
import ws.springframework.domain.Group;
import ws.springframework.domain.User;
import ws.springframework.exceptions.RegistredException;
import ws.springframework.forms.GroupForm;
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

//@Secured(value={"ROLE_Admin"}) a implementer apres
@RestController
public class UserRestController {


    private UserService userService;
    private GroupService groupService;


    @Autowired

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //user changes his/her full name
    @RequestMapping(value = "user/edit/fullName")
    public User editFullName(HttpServletRequest httpServletRequest, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString())
                .setFirstName(firstName);
        userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString())
                .setLastName(lastName);
        userService.saveUser(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));

        return userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString());

    }


    // user changes his/her biography
    @RequestMapping(value = "user/edit/biography")
    public User editBiography(HttpServletRequest httpServletRequest, @RequestParam("biography") String biography) {
        userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString())
                .setBiography(biography);

        userService.saveUser(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));

        return userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString());

    }

    //user deletes his/her account
    @RequestMapping(value = "user/delete", method = RequestMethod.DELETE)
    public ResponseEntity delete(HttpServletRequest httpServletRequest) {
        for (Group g : groupService.listAllGroups()) {
            if (g.getGroupMembers().contains(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString())))
                g.getGroupMembers().remove(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        }
        userService.deleteUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString());

        return new ResponseEntity(HttpStatus.OK);
    }

    //user creates new group

    @RequestMapping(value = "user/new/group", method = RequestMethod.POST)
    public ResponseEntity<Group> saveGroup(@RequestBody GroupForm form) {
        Group group = new Group();
        group.setName(form.getName());
        group.setAdminEmail(form.getAdminName());
        group.setDescription(form.getDescription());
        groupService.saveGroup(group);

        return new ResponseEntity<>(groupService.getGroupByName(group.getName()), HttpStatus.CREATED);

    }

    //edit description of a group he owns
    @RequestMapping(value = "user/group/edit")
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

    @RequestMapping(value = "user/groups", method = RequestMethod.GET)
    public ResponseEntity getAllGroupNamesAndCount() {

        return new ResponseEntity<>(groupService.getGroupNamesAndCount(), HttpStatus.OK);
    }

    //user joins group

    @RequestMapping(value = "user/group/join", method = RequestMethod.GET)
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
    @RequestMapping(value = "user/group/view", method = RequestMethod.GET)
    public Collection<User> viewMyGroupMembers(@RequestParam("name") String name) {
        return groupService.getGroupByName(name).getGroupMembers();
    }

    //user comments on the dashbord of the group
    // implementer entité commentairE avec un mapping one tomany avec group


    //user leaves the group
    @RequestMapping(value = "user/group/leave", method = RequestMethod.GET)
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
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public Collection<User> getUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return userService.findByFirstNameAndLastName(firstName, lastName);
    }

    // user comments on the dashboard of the group
    @RequestMapping(value = "comment", method = RequestMethod.GET)
    public Comment commentDashBoard(@RequestParam("GroupName") String GroupName, @RequestParam("comment") String comment) {
        groupService.addCommentToDashboard(groupService.getGroupByName(GroupName), groupService.getCommentByName(comment));
        return groupService.getCommentByName(comment);

    }


}
