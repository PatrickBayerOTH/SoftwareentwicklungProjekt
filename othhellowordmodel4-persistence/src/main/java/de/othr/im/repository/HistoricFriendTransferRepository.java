package de.othr.im.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.othr.im.model.Account;
import de.othr.im.model.HistoricFriendTransfer;

import de.othr.im.model.MoneyTransfer;


@Repository
public interface HistoricFriendTransferRepository  extends JpaRepository<HistoricFriendTransfer, Long> {

	@Query("select t from HistoricFriendTransfer t where t.senderid=:sender")
	List<HistoricFriendTransfer> findBysenderId(Long sender);

	@Query("select t from HistoricFriendTransfer t where t.receiverid=:receiver")
	List<HistoricFriendTransfer> findByreceiverId(Long receiver);
	
	

	
	
}