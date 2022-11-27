package de.othr.im.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import de.othr.im.model.WorkshopSubscription;


public interface WorkshopSubscriptionRepository extends JpaRepository<WorkshopSubscription, Long> {

	
	 //List<WorkshopSubscription> findWorkshopSubscriptionsByStudentId(Long studentId);
	@Query("select ws from WorkshopSubscription ws where ws.student.id=:idstudent")
	List<WorkshopSubscription> findWorkshopSubscriptionsbyStudent(@Param("idstudent") Long idstudent);
}

