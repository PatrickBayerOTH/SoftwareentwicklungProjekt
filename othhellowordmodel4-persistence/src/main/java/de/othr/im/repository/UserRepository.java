package de.othr.im.repository;


import de.othr.im.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByMatrikelnummer(Integer matrikelnummer);

    List<User> findByNameContaining(String name);




}
