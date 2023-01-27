package de.othr.im.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;


/* Abdallah Alsoudi
Entity, Tabelle. dient zum Speichern der spezifischen Daten von Manager beim Registrieren
 * die ist mit User Tabelle über Fremdschlüßel verbunden
 *  */
@Entity
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private int matrikelnummer;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMatrikelnummer() {
        return matrikelnummer;
    }

    public void setMatrikelnummer(int matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
