package de.othr.im.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "studentProfessor")
public class StudentProfessor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Integer matrikelnummer;

    private double kontostand;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMatrikelnummer() {
        return matrikelnummer;
    }

    public void setMatrikelnummer(Integer matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
    }

    public double getKontostand() {
        return kontostand;
    }

    public void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
