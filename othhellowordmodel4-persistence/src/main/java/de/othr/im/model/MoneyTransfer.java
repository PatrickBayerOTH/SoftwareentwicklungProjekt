package de.othr.im.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name="friends")
public class MoneyTransfer{// implements Serializable{
	
	
	
float amount;
	

	
	public float getAmount() {
		return amount;
	}
	public void setAmount( float amount) {
		this.amount=amount;
	}
	
	
	

}