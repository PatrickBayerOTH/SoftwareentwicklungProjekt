package de.othr.im.repository;

import java.util.Optional;

import de.othr.im.model.StudentProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/* Abdallah Alsoudi
 * Repository zum Abholen der Daten anhand des Fremdschlüssels und andere Attribute mit ID
 * */
@Repository
public interface StudentProfessorRepository extends JpaRepository<StudentProfessor, Long> {

    @Query("select s from StudentProfessor s where s.user.id=:iduser")
    Optional<StudentProfessor> findStudentByIdUser(Long iduser);
    @Query("select s from StudentProfessor s where s.account.id=:account")
    Optional<StudentProfessor> findByAccount(Long account);

}
