package de.othr.im.repository;

import de.othr.im.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/*
Repository holding account (the money holding kind) information
Written by Tobias Mooshofer
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(long id);
    void deleteById(Long id);
}
