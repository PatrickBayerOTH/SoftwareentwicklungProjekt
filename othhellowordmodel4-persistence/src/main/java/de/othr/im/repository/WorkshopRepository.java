package de.othr.im.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.othr.im.model.Workshop;


public interface WorkshopRepository extends JpaRepository<Workshop, Long>{
	
	
	@Query("select w from Workshop w where w.academicevent.id=:idevent")
	List<Workshop> findWorkshopsByAcademicEvent(@Param("idevent") Long idevent);
	
	

}
