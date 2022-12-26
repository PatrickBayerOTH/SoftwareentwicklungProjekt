package de.othr.im.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transfer")
public class MoneyTransfer{// implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;
	
	int from;
	int to;
	float amount;
	Timestamp date;

	
	public int getFrom() {
		return from;
	}
	public void setFrom( int fromid) {
		this.from=fromid;
	}
	
	public int getTo() {
		return to;
	}
	public void setTo(int toid) {
		this.to=toid;
	}
	
	
	public float getAmount() {
		return amount;
	}
	public void setAmount( float amount) {
		this.amount=amount;
	}
	
	public Timestamp getDate() {
		return date;
	}
	public void setDatetime( Timestamp date) {
		this.date=date;
	}
	
	

}