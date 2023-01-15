package de.othr.im.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

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



	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "from", referencedColumnName = "id")
	private Account from;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "to", referencedColumnName = "id")
	private Account to;
	
	float amount;
	Timestamp date;


	public Account getFrom() {
		return from;
	}

	public void setFrom(Account from) {
		this.from = from;
	}

	public Account getTo() {
		return to;
	}

	public void setTo(Account to) {
		this.to = to;
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

	public void setDate( Timestamp date) {
		this.date=date;
	}
	
	

}