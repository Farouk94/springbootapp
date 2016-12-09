package ws.springframework.services;


import ws.springframework.domain.Comment;
import ws.springframework.domain.Role;
import ws.springframework.domain.User;
import ws.springframework.exceptions.EntityNotFoundException;
import ws.springframework.repositories.CommentsRepository;
import ws.springframework.repositories.RoleRepository;
import ws.springframework.repositories.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by farou_000 on 29/10/2016.
 */

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CommentsRepository commentsRepository ;
    private Logger log = Logger.getLogger(UserServiceImp.class);


    @Autowired
    public void setCommentsRepository(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> listAllUsers() {
        return (Collection<User>) userRepository.findAll();
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findOne(id);
    }



    @Override
    public User getUserByEmail(String adresse) {
        return userRepository.findByEmailAdresse(adresse);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public void   deleteUserByEmail(String adresse) {
        userRepository.deleteByEmailAdresse(adresse);
    }


    @Override
    public Boolean ifExist(String adresse ) {
        return userRepository.existsByName(adresse);
    }

    @Override
    public Map<String, Object> getLoggedUser(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        SecurityContext securityContext = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String username = securityContext.getAuthentication().getName();
        List<String> roles = securityContext.getAuthentication().getAuthorities().stream().map((Function<GrantedAuthority, String>) GrantedAuthority::getAuthority).collect(Collectors.toList());
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("roles", roles);
        return params;

    }

    @Override
    @Transactional
    public void addRoleToUser(User user, Role role) {
        log.info("Adding role " + role.getName() + " to user " + user.getEmailAdresse());
        User adding_user = userRepository.findOne(user.getId());
        if (adding_user == null) {
            throw new EntityNotFoundException("User");
        }

        Role adding_role = roleRepository.findOne(role.getName());
        if (adding_role == null) {
            throw new EntityNotFoundException("Role");
        }

        adding_user.getRoles().add(adding_role);
        userRepository.save(adding_user);

    }


    public void addCommentToUser(User user , Comment comment) {

        log.info("Adding comment  " + comment.getComment() + " to user " + user.getEmailAdresse());
        User adding_user = userRepository.findOne(user.getId());
        if (adding_user == null) {
            throw new EntityNotFoundException("User");
        }

        Comment adding_comment = commentsRepository.findOne(comment.getId());
        if (adding_comment == null) {
            throw new EntityNotFoundException("Comment");
        }
adding_comment.setOwnerfirstName(adding_user.getFirstName());
        adding_comment.setOwnerLastName(adding_user.getLastName());

        commentsRepository.save(adding_comment);
        adding_user.getComments().add(adding_comment) ;
        userRepository.save(adding_user);

    }
    public Collection<User> findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }



}
