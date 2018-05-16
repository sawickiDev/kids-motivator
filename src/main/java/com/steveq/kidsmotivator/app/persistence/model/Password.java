package com.steveq.kidsmotivator.app.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "passwords_table")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pass_seq")
    @SequenceGenerator(name = "pass_seq", sequenceName = "passwords_seq", allocationSize = 1)
    @Column(name = "password_id")
    private int id;

    @Column(name = "password")
    private String password;

    public Password(){}

    public Password(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
