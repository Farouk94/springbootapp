package ws.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "ROLES")
public class Role implements Serializable {
    @Id
    private String name;
    private String descritption;


    public Role(String name, String descritption) {
        this.name = name;
        this.descritption = descritption;
    }

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescritption() {
        return descritption;
    }

    public void setDescritption(String descritption) {
        this.descritption = descritption;
    }
}
