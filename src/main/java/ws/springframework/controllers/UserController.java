package ws.springframework.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ws.springframework.domain.Comment;
import ws.springframework.domain.Group;
import ws.springframework.domain.User;
import ws.springframework.repositories.CommentsRepository;
import ws.springframework.services.GroupService;
import ws.springframework.services.UserService;

import java.util.Collection;
import java.util.HashSet;



@Controller
public class UserController {

    private UserService userService;
    private GroupService groupService;
    private CommentsRepository commentsRepository;

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

    //list all groups
    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("groups", groupService.listAllGroups());
        model.addAttribute("member", userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        return "groupsshow";
    }


    //edit the profil
    @RequestMapping(value = "edit/profil", method = RequestMethod.GET)
    public String setProfilform(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("user", userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));

        return "editprofil";
    }

    //edit the profil
    @RequestMapping(value = "editedprofil", method = RequestMethod.POST)
    public String setProfilSubmit(User user, HttpServletRequest httpServletRequest, Model model) {
        User user1 = userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString());
        user1.setBiography(user.getBiography());
        user1.setLastName(user.getLastName());
        ;
        user1.setFirstName(user.getFirstName());
        userService.saveUser(user1);

        return "redirect:/profil";
    }


    // show the profil
    @RequestMapping("profil")
    public String showProfil(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("user", userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        return "profil";
    }

    // show member list of  a specific group
    @RequestMapping(value = "memberlist/{id}", method = RequestMethod.GET)
    public String showmemberlist(@PathVariable Integer id, Model model) {
        model.addAttribute("group", groupService.getGroupById(id));
        model.addAttribute("members", groupService.getGroupById(id).getGroupMembers());

        return "memberlist";
    }

    // show groups that user art part of and he owns
    @RequestMapping(value = "mygroups", method = RequestMethod.GET)
    public String mygroups(HttpServletRequest httpServletRequest, Model model) {
        Collection<Group> partGroups = new HashSet();
        //g.getAdminEmail().equals(userService.getLoggedUser(httpServletRequest).get("username").toString())
        model.addAttribute("mygroups", groupService.getGroupByAdminEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        for (Group g : groupService.listAllGroups()) {
            if (g.getGroupMembers().contains(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()))) {
                partGroups.add(g);
            }
        }

        model.addAttribute("partgroups", partGroups);

        return "groupsowner";
    }

    // delete the profil of the user
    @Transactional
    //user deletes his/her account
    @RequestMapping(value = "user/deleteprofil", method = RequestMethod.GET)
    public String delete(HttpServletRequest httpServletRequest) {
        for (Group g : groupService.listAllGroups()) {
            if (g.getGroupMembers().contains(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString())))
                g.getGroupMembers().remove(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        }
        userService.deleteUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString());


        return "redirect:/login";
    }

    // leave  a group
    @Transactional
    @RequestMapping(value = "leavegroup/{id}", method = RequestMethod.GET)
    public String leavegroup(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        groupService.getGroupById(id).getGroupMembers().remove(userService.getUserByEmail
                (userService.getLoggedUser(httpServletRequest).get("username").toString()));


        return "redirect:/mygroups";
    }

    //delete a group
    @Transactional
    //user deletes his/her group
    @RequestMapping(value = "user/deletegroup/{id}", method = RequestMethod.GET)
    public String deletegroup(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        Collection<User> toremove = new HashSet();
        for (User u : groupService.getGroupById(id).getGroupMembers()) {
            if (!u.equals(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()))) {
                toremove.add(u);
            }
        }
        groupService.getGroupById(id).getGroupMembers().removeAll(toremove);
        groupService.deleteGroup(groupService.getGroupById(id).getName());

        return "redirect:/mygroups";
    }


    @Transactional
    //user join a group
    @RequestMapping(value = "join/{id}", method = RequestMethod.GET)
    public String joingroup(@PathVariable Integer id, HttpServletRequest httpServletRequest) {


        groupService.getGroupById(id).getGroupMembers().add(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));


        return "redirect:/groups";
    }

    //user edit information about the group he owns
    @Transactional

    @RequestMapping(value = "editgroup/{id}", method = RequestMethod.GET)
    public String editgroup(Model model, @PathVariable Integer id) {


        model.addAttribute("editedgroup", groupService.getGroupById(id));


        return "editgroup";
    }

    // user edit informations about the group he owns
    @RequestMapping(value = "editedgroup/{id}", method = RequestMethod.POST)
    public String setGroupSubmit(Group group, @PathVariable Integer id, HttpServletRequest httpServletRequest) {

        Group group1 = groupService.getGroupById(id);
        group1.setAdminEmail(userService.getLoggedUser(httpServletRequest).get("username").toString());
        group1.setName(group.getName());
        group1.setDescription(group.getDescription());
        groupService.saveGroup(group1);
        if (!group1.getGroupMembers().contains(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()))) {
            groupService.addUserToGroup(group1, userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        }


        return "redirect:/mygroups";


    }

    // user create a group
    @RequestMapping(value = "creategroup", method = RequestMethod.GET)
    public String newGroup(Model model) {


        Group group2 = new Group();
        model.addAttribute("newgroup", group2);
        groupService.saveGroup(group2);

        return "creategroup";


    }
// user see other users

    @RequestMapping(value = "otherusers", method = RequestMethod.GET)
    public String otherUsers(Model model, HttpServletRequest httpServletRequest) {

        Collection<User> users = new HashSet();
        users = userService.listAllUsers();

        users.remove(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        System.out.println(users.toString());
        model.addAttribute("users", users);
        return "users";


    }

    //user see comments of a group
    @RequestMapping(value = "comments/{id}", method = RequestMethod.GET)
    public String seecomments(Model model, @PathVariable Integer id) {
        Collection<User> users = new HashSet();
        Collection<Integer> ids = new HashSet();

        model.addAttribute("commentedgroup", groupService.getGroupById(id));
        model.addAttribute("comments", groupService.getGroupById(id).getDiscutionBoard());

        return "comments";


    }

    //user comment a group
    @RequestMapping(value = "docomment/{id}", method = RequestMethod.GET)
    public String docomments(Model model, @PathVariable Integer id) {


        Comment comment = new Comment();

        commentsRepository.save(comment);
        model.addAttribute("newcomment", comment);
        return "commentcreate";


    }


    @RequestMapping(value = "editedcomment/{id}", method = RequestMethod.POST)
    public String setCommentSubmit(Comment comment, @PathVariable Integer id, HttpServletRequest httpServletRequest) {

        String email = userService.getLoggedUser(httpServletRequest).get("username").toString();
        Comment comment1 = groupService.getCommentById(id);
        System.out.println(comment.getComment());
        comment1.setComment(comment.getComment());
        comment1.setOwnerfirstName(userService.getFNofCommentOwner(email));
        comment1.setOwnerLastName(userService.getLNofCommentOwner(email));
        commentsRepository.save(comment1);


        userService.addCommentToUser((userService.getUserByEmail(email)), comment1);

        //  groupService.addCommentToDashboard(groupService.getGroupById(id),comment);
//Problem here how to get the id of the group we selected ?


        return "redirect:/comments/{id}";


    }

}
