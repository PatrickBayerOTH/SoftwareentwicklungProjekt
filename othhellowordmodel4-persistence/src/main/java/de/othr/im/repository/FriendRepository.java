package de.othr.im.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.im.model.Friend;
import org.springframework.transaction.annotation.Transactional;

/*
Repository holding friendship information
Written by Patrick Bayer
 */

public interface FriendRepository extends JpaRepository<Friend, Long> {
	
	List<Friend> findByfriendId(Long userid);
	List<Friend> findByuserId(Long userid);
	Friend findByuserIdAndFriendId(Long userid, Long friendid);
	long deleteByuserIdAndFriendId(Long userid, Long friendid);
	long deleteByFriendIdAndUserId(Long friendid, Long userid);


	@Transactional
	long deleteByuserId(Long valueOf);

	@Transactional
	long deleteByFriendId(Long valueOf);

}
