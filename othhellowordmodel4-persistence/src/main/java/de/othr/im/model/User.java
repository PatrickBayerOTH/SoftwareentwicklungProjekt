package de.othr.im.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


/* Abdallah Alsoudi
Entity, Tabelle. dient zum Speichern der Daten von User beim Registrieren
* */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private Integer matrikelnummer;

    @Column(unique = true, nullable = false)
    @Email(message = "Please, inform a valid E-Mail!")
    private String email;

    @Size(min = 3, message = "Nachname must contain at least 3 characters!")
    private String nachname;

    @Size(min = 3, message = "Name must contain at least 3 characters!")
    private String name;

    @Size(min = 5, message = "Password must contain at least 5 characters!")
    private String password;

    private String type;

    private Integer active;


    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "userauthority",
            joinColumns = @JoinColumn(name = "iduser"),
            inverseJoinColumns = @JoinColumn(name = "idauthority")
    )
    private List<Authority> myauthorities = new ArrayList<Authority>();

    public List<Authority> getMyauthorities() {
        return myauthorities;
    }

    public void setMyauthorities(List<Authority> myauthorities) {
        this.myauthorities = myauthorities;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AuthenticationProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }
}
