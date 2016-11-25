package guru.springframework.controllers;

import guru.springframework.domain.Group;
import guru.springframework.domain.User;
import guru.springframework.exceptions.EntityNotFoundException;
import guru.springframework.services.GroupService;
import guru.springframework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by farou_000 on 31/10/2016.
 */

@RestController
public class AdminController {
    private GroupService groupService;
    private UserService userService;

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "json/groups", method = RequestMethod.GET)
    public ResponseEntity<Collection<Group>> getAllGroups() {

        return new ResponseEntity<>(groupService.listAllGroups(), HttpStatus.OK);
    }


    @RequestMapping("json/group/{id}")
    public ResponseEntity<?> showGroup(@PathVariable String name) {
        return new ResponseEntity<>(groupService.getGroupByName(name), HttpStatus.OK);
      /*  if (groupService.ifExist(name)) {
            return new ResponseEntity<>(groupService.getGroupByName(name), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new EntityNotFoundException("Group not found").getMessage(), HttpStatus.NOT_FOUND);
*/
    }

    @RequestMapping("json/group/edit/{name}")
    public ResponseEntity<Group> edit(@PathVariable String name) {
        return new ResponseEntity<>(groupService.getGroupByName(name), HttpStatus.OK);
    }

    @RequestMapping("json/group/new")
    public ResponseEntity<Group> newBlankGroup() {

        return new ResponseEntity<>(new Group(), HttpStatus.OK);
    }

    @RequestMapping(value = "json/group", method = RequestMethod.POST)
    public ResponseEntity<Group> saveGroup(@RequestBody Group group) {

        //  return "redirect:/product/" + product.getId();
        return new ResponseEntity<>(groupService.saveGroup(group), HttpStatus.CREATED);

    }

    @RequestMapping(value = "json/group/delete/{name}", method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable String name) {
        groupService.deleteGroup(name);
        return new ResponseEntity(HttpStatus.OK);
    }

    //users
    @RequestMapping(value = "json/users", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getAllUsers() {

        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
    }

    @RequestMapping("json/user/{id}")
    public ResponseEntity<?> showUser(@PathVariable String address) {

        if (userService.ifExist(address)) {
            return new ResponseEntity<>(userService.getUserByEmail(address), HttpStatus.OK);
        } else return new ResponseEntity<>(new EntityNotFoundException(address).getMessage(), HttpStatus.NOT_FOUND);

    }

    @RequestMapping("json/user/edit/{address}")
    public ResponseEntity<User> editUser(@PathVariable String address) {
        return new ResponseEntity<>(userService.getUserByEmail(address), HttpStatus.OK);
    }


    @RequestMapping("json/user/new")
    public ResponseEntity<User> newBlank() {

        return new ResponseEntity<>(new User(), HttpStatus.OK);
    }

    @RequestMapping(value = "json/user", method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user) {


        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);

    }

    @RequestMapping(value = "user/delete/{address}", method = RequestMethod.GET)
    public ResponseEntity deleteUser(@PathVariable String address) {
        userService.deleteUserByEmail(address);
        return new ResponseEntity(HttpStatus.OK);
    }


}
