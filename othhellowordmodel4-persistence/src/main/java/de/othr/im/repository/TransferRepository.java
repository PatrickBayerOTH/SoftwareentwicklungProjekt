package de.othr.im.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.im.model.MoneyTransfer;
//import de.othr.im.model.Student;

public interface TransferRepository extends JpaRepository<MoneyTransfer, Long> {

	List<MoneyTransfer> findByFrom(int from);
	List<MoneyTransfer> findByTo(int to);
	
	
	
}