

package de.othr.im.repository;

import de.othr.im.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserEmailConfirmationRepository extends CrudRepository<User, String> {

    User findByEmailIgnoreCase(String email);


}


