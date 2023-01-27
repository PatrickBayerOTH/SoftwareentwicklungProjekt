package de.othr.im.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Patrick Bayer Class for Friend entity


@Entity
@Table(name="friends")
public class Friend implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Id of the friend relation
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;
	
	//Id of the user who initiated the friendship
	@Column(name = "userid")
	Long userId;
	//Id of the user who got added
	@Column(name = "friendid")
	Long friendId;
	

	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userid) {
		this.userId = userid;
	}
	public Long getFriendId() {
		return friendId;
	}
	public void setFriendId(Long friendid) {
		this.friendId = friendid;
	}
	
	

}
