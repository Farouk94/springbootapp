package guru.springframework.controllers;

import guru.springframework.domain.Group;

import guru.springframework.domain.User;
import guru.springframework.services.GroupService;
import guru.springframework.services.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by farou_000 on 04/11/2016.
 */


@Controller
public class UserController {

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


    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("groups", groupService.listAllGroups());
        model.addAttribute("member", userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        return "groupsshow";
    }

    @RequestMapping(value = "edit/profil", method = RequestMethod.GET)
    public String setProfilform(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("user", userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));

        return "editprofil";
    }

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

    @RequestMapping("profil")
    public String showProfil(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("user", userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        return "profil";
    }


    @RequestMapping(value = "memberlist/{id}", method = RequestMethod.GET)
    public String showmemberlist(@PathVariable Integer id, Model model) {
        model.addAttribute("group", groupService.getGroupById(id));
        model.addAttribute("members", groupService.getGroupById(id).getGroupMembers());

        return "memberlist";
    }

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

    @Transactional
    @RequestMapping(value = "leavegroup/{id}", method = RequestMethod.GET)
    public String leavegroup(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        groupService.getGroupById(id).getGroupMembers().remove(userService.getUserByEmail
                (userService.getLoggedUser(httpServletRequest).get("username").toString()));


        return "redirect:/mygroups";
    }


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
    //user deletes his/her group
    @RequestMapping(value = "join/{id}", method = RequestMethod.GET)
    public String joingroup(@PathVariable Integer id, HttpServletRequest httpServletRequest) {


        groupService.getGroupById(id).getGroupMembers().add(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));


        return "redirect:/groups";
    }

    @Transactional
    //user deletes his/her group
    @RequestMapping(value = "editgroup/{id}", method = RequestMethod.GET)
    public String editgroup(Model model, @PathVariable Integer id) {


        model.addAttribute("editedgroup", groupService.getGroupById(id));


        return "editgroup";
    }


    @RequestMapping(value = "editedgroup/{id}", method = RequestMethod.POST)
    public String setGroupSubmit(Group group, @PathVariable Integer id , HttpServletRequest httpServletRequest) {

        Group group1 = groupService.getGroupById(id);
        group1.setAdminEmail(userService.getLoggedUser(httpServletRequest).get("username").toString());
        group1.setName(group.getName());
        group1.setDescription(group.getDescription());
        groupService.saveGroup(group1);
        if(!group1.getGroupMembers().contains(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()))) {
            groupService.addUserToGroup(group1, userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        }

        return "redirect:/mygroups";


    }

    @RequestMapping(value = "creategroup", method = RequestMethod.GET)
    public String newGroup(Model model) {
Group group = new Group();
        groupService.saveGroup(group);

        model.addAttribute("newgroup", group);
        return "creategroup";


    }


    @RequestMapping(value = "otherusers", method = RequestMethod.GET)
    public String otherUsers(Model model , HttpServletRequest httpServletRequest) {

        Collection<User> users = new HashSet();
        users = userService.listAllUsers();

        users.remove(userService.getUserByEmail(userService.getLoggedUser(httpServletRequest).get("username").toString()));
        System.out.println(users.toString());
        model.addAttribute("users", users);
        return "users";


    }






}
