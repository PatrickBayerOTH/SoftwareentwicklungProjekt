package de.othr.im.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import de.othr.im.model.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {

	List<Student> findByNameContaining(String name);
	Optional<Student> findById(Long id);
	
	
}
