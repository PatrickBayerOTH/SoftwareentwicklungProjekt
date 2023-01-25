package de.othr.im.repository;


import de.othr.im.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.matrikelnummer = :matrikelnummer")
    Optional<User> findUserMtnr(Integer matrikelnummer);
    
    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findByNameContaining(String name);
    
    @Query("SELECT u FROM User u WHERE u.nachname = :nachname")
	List<User> findByNachnameContaining(String nachname);


}
