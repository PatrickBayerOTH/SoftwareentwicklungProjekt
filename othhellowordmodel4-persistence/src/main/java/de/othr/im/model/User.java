package de.othr.im.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Integer matrikelnummer;

    @Column(nullable = false, updatable = false)
    @Email(message = "Please, inform a valid E-Mail!")
    private String email;

    @Column(nullable = false)
    @Size(min = 4, message = "Nachname must contain at least 4 characters!")
    private String nachname;

    @Column(nullable = false)
    @Size(min = 4, message = "Name must contain at least 4 characters!")
    private String name;

    @Column(nullable = false)
    @Size(min = 6, message = "Password must contain at least 6 characters!")
    private String password;

    @Column(nullable = false, updatable = false)
    private String type;

    private Integer active;


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

}
