package ws.springframework.controllers;

import ws.springframework.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by farou_000 on 29/10/2016.
 */
// ne pas toucher aussi
@RestController
//@Secured(value={"ROLE_Admin"}) a implementer apres

public class GroupController {
    private GroupService groupService;


    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }


}
