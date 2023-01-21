
package de.othr.im.repository;

import de.othr.im.model.ConfirmationToken;
import de.othr.im.model.StudentProfessor;
import de.othr.im.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);

    @Query("select c from ConfirmationToken c where c.user.id=:iduser")
    Optional<ConfirmationToken> findStudentByIdUser(Long iduser);

}

