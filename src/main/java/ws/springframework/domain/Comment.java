package ws.springframework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;




/**
 * Created by farou_000 on 02/11/2016.
 */

@Entity
@Table(name = "COMMENTS")

public class Comment implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer Id;
    private String OwnerfirstName;
    private String OwnerLastName ;
    private String comment;

    public String getOwnerfirstName() {
        return OwnerfirstName;
    }

    public void setOwnerfirstName(String ownerfirstName) {
        OwnerfirstName = ownerfirstName;
    }

    public String getOwnerLastName() {
        return OwnerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        OwnerLastName = ownerLastName;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Comment() {

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
