package de.othr.im.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.im.model.Friend;
import de.othr.im.model.Student;

public interface FriendRepository extends JpaRepository<Friend, Long> {
	
	List<Friend> findByuserId(Long userid);
	Friend findByuserIdAndFriendId(Long userid, Long friendid);
	long deleteByuserIdAndFriendId(Long userid, Long friendid);
}
