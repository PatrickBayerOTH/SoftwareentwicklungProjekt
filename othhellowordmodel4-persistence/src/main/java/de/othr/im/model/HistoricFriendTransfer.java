package de.othr.im.model;

import java.sql.Timestamp;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.othr.im.util.AttributeEncryptor;

//Patrick Bayer Class for saving transfers even when one user deletes his account and 
 //therefore information about the  receiver would be not accessible anymore 


@Entity
@Table(name = "histtransfer")
public class HistoricFriendTransfer {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	

	
	//Id of the sender
	@Column(name = "senderid")
	Long senderid;
	//Id of the receiver
	@Column(name = "receiverid")
	Long receiverid;
	//Name of the sender firstname + lastname
	@Column(name = "sender")
	String sender;
	//name of the receiver firstname + lastname
	@Column(name = "receiver")
	String receiver;
	//Amount of the transaction
	@Column(name = "amount")
	double amount;
	//Date and time of the transaction
	@Column(name = "date")
	Timestamp date;
	//Message sent with the transaction
	@Convert(converter = AttributeEncryptor.class)
	String message;
	
	
	
	public Long getSenderid() {
		return senderid;
	}

	public void setSenderid(Long senderid) {
		this.senderid = senderid;
	}

	public Long getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(Long receiverid) {
		this.receiverid = receiverid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String string) {
		this.sender = string;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
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
