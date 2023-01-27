

package de.othr.im.repository;

import de.othr.im.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



/* Abdallah Alsoudi
 * Repository zum Suchen nach der Email von der DB nur für Überprüfung von Token hilfreich und anhand der IDs
 * */
@Repository
public interface UserEmailConfirmationRepository extends CrudRepository<User, String> {

    User findByEmailIgnoreCase(String email);


}


