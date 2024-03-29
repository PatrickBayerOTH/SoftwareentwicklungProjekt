package de.othr.im.model;

import javax.persistence.*;

/*
Table representing an corporate user account
consists of an ID, a name and an associated account (the money holding type)
Written by Tobias Mooshofer
 */
@Entity
@Table(name = "corporate")
public class Corporate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "accountid", referencedColumnName = "id")
    private Account account;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
