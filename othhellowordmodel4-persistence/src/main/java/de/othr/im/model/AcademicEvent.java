package de.othr.im.model;
import java.time.LocalDate;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "academicevent")

public class AcademicEvent {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;
	
    @Size(min = 6, message = "Title must have at least 6 characters")
	String title;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@javax.validation.constraints.NotNull (message="Please, inform a valid date!")
    LocalDate date;

	@OneToMany(mappedBy = "academicevent", cascade = CascadeType.ALL)
	List <Workshop> workshops;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public List<Workshop> getWorkshops() {
		return workshops;
	}

	public void setWorkshops(List<Workshop> workshops) {
		this.workshops = workshops;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	

	

	
	
}
