/*
package de.othr.im.model;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "userauthority")
public class Userauthority {

    @Id
    private Long id;

    private Long idauthority;


    @OneToOne (cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "id")
    User user;

    @OneToOne (cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "idauthority")
    Authority authority;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getIdauthority() {
        return idauthority;
    }

    public void setIdauthority(Long idauthority) {
        this.idauthority = idauthority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}
*/
