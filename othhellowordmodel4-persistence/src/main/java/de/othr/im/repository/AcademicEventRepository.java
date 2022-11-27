package de.othr.im.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.im.model.AcademicEvent;


public interface AcademicEventRepository extends JpaRepository<AcademicEvent, Long> {

	List<AcademicEvent> findByTitleContaining(String title);
	
}
