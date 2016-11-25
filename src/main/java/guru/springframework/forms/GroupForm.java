package guru.springframework.forms;

import java.io.Serializable;

/**
 * Created by farou_000 on 01/11/2016.
 */
public class GroupForm implements Serializable {


    private String name;

    private String description;

    private String adminName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
