package ws.springframework.repositories;


import ws.springframework.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface UserRepository extends CrudRepository<User, Integer> {



    Collection<User> findByFirstNameAndLastName(String firstName, String lastName);
    User findByEmailAdresse(String adresse);
    void deleteByEmailAdresse(String adresse);
    @Query(value = "select exists(select email_adresse from users where email_adresse=?1)", nativeQuery = true)
    public Boolean existsByName(String adresse);

    @Query(value = "select first_name from users where email_adresse=?1" , nativeQuery = true)
    public String getFNofCommentOwner(String adresse) ;
    @Query(value = "select last_name from users where email_adresse=?1" , nativeQuery = true)
    public String getLNofCommentOwner(String adresse) ;

}