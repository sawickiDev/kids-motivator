package com.steveq.kidsmotivator.app.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles_table")
public class Role {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
//    @SequenceGenerator(name = "role_seq", sequenceName = "roles_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @NotNull
    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {}

    public Role(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
