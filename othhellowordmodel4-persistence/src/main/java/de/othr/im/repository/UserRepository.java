package de.othr.im.repository;


import de.othr.im.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/* Abdallah Alsoudi
 * Repository zum Abholen der Daten anhand des Fremdschlüssels und andere Attribute mit Email oder ID
 * */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.matrikelnummer = :matrikelnummer")
    Optional<User> findUserMtnr(Integer matrikelnummer);
    
    
    List<User> findAllByNameContaining(String name);
    

	List<User> findAllByNachnameContaining(String nachname);


}
