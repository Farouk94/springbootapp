package ws.springframework.services;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import ws.springframework.domain.Comment;
import ws.springframework.domain.Role;
import ws.springframework.domain.User;

import java.util.Collection;
import java.util.Map;


public interface UserService {

    Collection<User> listAllUsers();

    User getUser(Integer id);


    User getUserByEmail(String adresse);

    User saveUser(User user);

    void deleteUser(Integer id);

    void deleteUserByEmail(String adresse);

    Boolean ifExist(String adresse);

    Map<String, Object> getLoggedUser(HttpServletRequest httpServletRequest);

    public void addRoleToUser(User user, Role role);

    public void addCommentToUser(User user, Comment comment);

    public Collection<User> findByFirstNameAndLastName(String firstName, String lastName);

    public String getFNofCommentOwner(String adresse);

    public String getLNofCommentOwner(String adresse);


}
