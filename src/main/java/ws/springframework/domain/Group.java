package ws.springframework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "GROUPS", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})


public class Group implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore

    private Integer Id;

    private String name;

    private String description;

    private String adminEmail;
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "group_users")
    private Collection<User> groupMembers;
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "group_comments")
    private Collection<Comment> discutionBoard;

    public Group() {

    }

    public Collection<Comment> getDiscutionBoard() {
        return discutionBoard;
    }

    public void setDiscutionBoard(Collection<Comment> discutionBoard) {
        this.discutionBoard = discutionBoard;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

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

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public Collection<User> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(Collection<User> groupMembers) {
        this.groupMembers = groupMembers;
    }


}