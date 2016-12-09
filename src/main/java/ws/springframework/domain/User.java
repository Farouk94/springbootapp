package ws.springframework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"emailAdresse"})})

public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer Id;
    @JsonIgnore
    private String emailAdresse;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String biography;
    @JsonIgnore
    private Boolean enabled;
    @ManyToMany
    @JoinTable(name = "USERS_ROLES")
    @JsonIgnore
    private Collection<Role> roles;

    @OneToMany (fetch = FetchType.EAGER )
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "user_comments")
    private Collection<Comment> comments;

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public User() {

    }

    public User(String emailAdresse, String firstName, String lastName, String biography) {
        super();
        this.emailAdresse = emailAdresse;
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAdresse() {
        return emailAdresse;
    }

    public void setEmailAdresse(String emailAdresse) {
        this.emailAdresse = emailAdresse;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", emailAdresse='" + emailAdresse + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", biography='" + biography + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}