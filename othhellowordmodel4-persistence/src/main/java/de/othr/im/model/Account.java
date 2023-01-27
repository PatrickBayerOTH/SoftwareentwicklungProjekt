package de.othr.im.model;


import javax.persistence.*;

/*
Table representing an account
consists of an ID and the current amount of money (value) the account holds
Written by Tobias Mooshofer
 */
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", columnDefinition = "double default 0")
    private double value;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void addValue(double value) {
        this.value = this.value + value;
    }








}
