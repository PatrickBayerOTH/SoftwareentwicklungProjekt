package de.othr.im.model;

import java.sql.Timestamp;

import javax.persistence.*;

import de.othr.im.util.AttributeEncryptor;

//Patrick Bayer Class for Transfer which holds transfers of active User. 
//This entity and the correspondinf table is used by Tobi too for the account history

@Entity
@Table(name = "transfer")
public class MoneyTransfer {// implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	// Id referencing sending user
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "sender", referencedColumnName = "id")
	private Account sender;
	// Id referencing receiving user
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "receiver", referencedColumnName = "id")
	private Account receiver;
    //Amount of the transfer
	double amount;
	//Date ans time of the transfer
	Timestamp date;
	//Message sent with the transfer
	@Convert(converter = AttributeEncryptor.class)
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Account getSender() {
		return sender;
	}

	public void setSender(Account sender) {
		this.sender = sender;
	}

	public Account getReceiver() {
		return receiver;
	}

	public void setReceiver(Account receiver) {
		this.receiver = receiver;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

}