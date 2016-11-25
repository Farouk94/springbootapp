package guru.springframework.services;

import guru.springframework.domain.Role;
import guru.springframework.domain.User;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import java.util.Collection;
import java.util.Map;

/**
 * Created by farou_000 on 29/10/2016.
 */
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

    public Collection<User> findByFirstNameAndLastName(String firstName, String lastName);

}
