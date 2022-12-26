package de.othr.im.repository;

import de.othr.im.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    @Query("select m from Manager m where m.user.id=:iduser")
    Optional<Manager> findManagerByIdUser(Long iduser);

}
