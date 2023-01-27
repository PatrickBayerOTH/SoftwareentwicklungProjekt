
package de.othr.im.repository;

import de.othr.im.model.ConfirmationToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/* Abdallah Alsoudi
* Repository zum Abholen der Daten von der DB für Überprüfung von Token und anhand der IDs
* */
@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);

    @Query("select c from ConfirmationToken c where c.user.id=:iduser")
    Optional<ConfirmationToken> findStudentByIdUser(Long iduser);

}

