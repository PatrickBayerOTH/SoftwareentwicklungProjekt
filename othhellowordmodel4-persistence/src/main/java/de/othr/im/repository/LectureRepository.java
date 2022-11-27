package de.othr.im.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.othr.im.model.Lecture;


public interface LectureRepository extends JpaRepository<Lecture, Long>{

	@Query("select l from Lecture l where l.academicevent.id=:idevent")
	List<Lecture> findLecturesByAcademicEvent(Long idevent);

}
