package de.othr.im.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.im.model.*;
import org.springframework.data.jpa.repository.Query;
//import de.othr.im.model.Student;

public interface TransferRepository extends JpaRepository<MoneyTransfer, Account> {

	@Query("select t from MoneyTransfer t where t.sender.id=:sender")
	List<MoneyTransfer> findBySender(Long sender);

	@Query("select t from MoneyTransfer t where t.receiver.id=:receiver")
	List<MoneyTransfer> findByReceiver(Long receiver);

	
	
}