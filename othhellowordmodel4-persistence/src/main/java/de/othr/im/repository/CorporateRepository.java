package de.othr.im.repository;

import de.othr.im.model.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/*
Repository holding information about corporate user accounts
Written by Tobias Mooshofer
 */
public interface CorporateRepository extends JpaRepository<Corporate, Long> {

    Optional<Corporate> findById(Long id);
    Optional<Corporate> findByName(String name);

    @Query("select c from Corporate c where c.account.id=:account")
    Optional<Corporate> findByAccount(Long account);

    void deleteById(Long id);
}
