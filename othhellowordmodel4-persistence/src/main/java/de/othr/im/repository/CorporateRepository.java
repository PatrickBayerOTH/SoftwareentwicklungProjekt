package de.othr.im.repository;

import de.othr.im.model.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorporateRepository extends JpaRepository<Corporate, Long> {

    Optional<Corporate> findById(Long id);
    Optional<Corporate> findByName(String name);

    Optional<Corporate> findByAccount(Long account);
}
