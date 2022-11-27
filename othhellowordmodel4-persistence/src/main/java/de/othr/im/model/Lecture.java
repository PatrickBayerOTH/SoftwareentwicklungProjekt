package de.othr.im.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="lecture")

public class Lecture {
		
		@Id
		@GeneratedValue (strategy = GenerationType.IDENTITY)
		Long id;
		
		@Size(min = 5, message = "Size should have at least 5 ")
		String title;
		
		@ManyToOne
	    @JoinColumn(name = "idacademicevent")
		AcademicEvent academicevent;

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

		public AcademicEvent getAcademicevent() {
			return academicevent;
		}

		public void setAcademicevent(AcademicEvent academicevent) {
			this.academicevent = academicevent;
		}

}
