package com.steveq.kidsmotivator.app.persistence.model;

import com.steveq.kidsmotivator.app.dashboard.validation.ValidPassword;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "passwords_table")
@ValidPassword
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pass_seq")
    @SequenceGenerator(name = "pass_seq", sequenceName = "passwords_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_id")
    private int id;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
