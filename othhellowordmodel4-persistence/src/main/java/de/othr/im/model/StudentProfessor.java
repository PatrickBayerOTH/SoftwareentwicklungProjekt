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

    @Column(unique = true)
    private Integer matrikelnummer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountid", referencedColumnName = "id")
    Account account;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
