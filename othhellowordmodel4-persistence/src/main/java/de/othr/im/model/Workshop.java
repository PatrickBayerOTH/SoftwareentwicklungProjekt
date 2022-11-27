package de.othr.im.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="workshop")
public class Workshop {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
		
	private String title;

	@ManyToOne
    @JoinColumn(name = "idacademicevent")
	AcademicEvent academicevent;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@OneToMany(mappedBy = "workshop")
	Set<WorkshopSubscription> workshopSubscriptions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Set<WorkshopSubscription> getWorkshopSubscriptions() {
		return workshopSubscriptions;
	}

	public void setWorkshopSubscriptions(Set<WorkshopSubscription> workshopSubscriptions) {
		this.workshopSubscriptions = workshopSubscriptions;
	}

	public AcademicEvent getAcademicevent() {
		return academicevent;
	}

	public void setAcademicevent(AcademicEvent academicevent) {
		this.academicevent = academicevent;
	}

	
}
