package com.steveq.kidsmotivator.app.auth.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Size(min = 3, max = 20)
    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {}

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        if (id >= 0)
            this.id = id;
        else
            this.id = -1;
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
                ", users=" + users.stream().map(User::getId).collect(Collectors.toList()) +
                '}';
    }
}
