package de.othr.im.model;
import javax.persistence.JoinColumn;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Lazy;

@Entity
@Table(name="student")
public class Student implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;

	String name;
	String email;
	float kontostand;
	
//	@OneToMany(mappedBy = "student")
//	List<Student> friends;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setKontostand(float addValue) {
		this.kontostand = kontostand+addValue;
	}
	
	public float getKontostand() {
		return kontostand;
	}
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
