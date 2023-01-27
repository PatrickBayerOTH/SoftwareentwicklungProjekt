
package de.othr.im.model;

import java.sql.Timestamp;

import javax.persistence.*;

import de.othr.im.util.AttributeEncryptor;

/*
Table holding information about transfers
Transfers are represented by:
- a sending and a receiving account
- a value for the amount sent 
- a timestamp
- a message sent with the transfer encrypted and decrypted by AttributeEncryptor

Written by Patrick Bayer & Tobias Mooshofer
 */
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

	// Sending account
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "sender", referencedColumnName = "id")
	private Account sender;
	// Receiving account
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "receiver", referencedColumnName = "id")
	private Account receiver;
	//Amount of the transaction 
	double amount;
	//Date and time of the transaction
	Timestamp date;
	//Message sent with the transaction
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