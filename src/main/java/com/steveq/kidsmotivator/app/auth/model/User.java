package com.steveq.kidsmotivator.app.auth.model;

import com.steveq.kidsmotivator.app.missions.model.Mission;
import com.steveq.kidsmotivator.app.prizes.model.Prize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users_table")
public class User implements UserDetails {

    // table mapping

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
//    @SequenceGenerator(name = "user_seq", sequenceName = "users_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "user_name")
    private String userName;

    @Column(name = "active")
    private Boolean active = true;

    @NotNull
    @Valid
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id")
    private Password pass;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_role_table",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "parent_child_table",
            joinColumns = { @JoinColumn(name = "parent_id") },
            inverseJoinColumns = { @JoinColumn(name = "child_id") }
    )
    private Set<User> children = new HashSet<>();

    @ManyToMany(mappedBy = "children")
    private Set<User> parents = new HashSet<>();

    @OneToMany(mappedBy = "assignedKid")
    private Set<Mission> missions = new HashSet<>();;

    @OneToMany(mappedBy = "owner")
    private Set<Mission> ownedMissions = new HashSet<>();;

    @OneToMany(mappedBy = "owner")
    private Set<Prize> ownedPrizes = new HashSet<>();;

    @OneToMany(mappedBy = "assignee")
    private Set<Prize> takenPrizes = new HashSet<>();;

    // table mapping


    //helper fields

    @Transient
    private int sumPoints;

    @Transient
    private int sumMissions;

    // helper fields


    public User(){}

    // getters - setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Password getPass() {
        return pass;
    }

    public void setPass(Password password) {
        this.pass = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (role != null) {
            this.roles.add(role);
        }
    }

    public Set<User> getChildren() {
        return children;
    }

    public void addChildren(User children) {
        if (children != null) {
            this.children.add(children);
        }
    }

    public void addParent(User parent) {
        if (parent != null) {
            this.parents.add(parent);
        }
    }

    public Set<User> getParents() {
        return parents;
    }

    public Set<Mission> getMissions() {
        return missions;
    }

    public void setMissions(Set<Mission> missions) {
        this.missions = missions;
    }

    public void addMission(Mission mission) {
        if (mission != null)
            this.missions.add(mission);
    }

    public Set<Mission> getOwnedMissions() {
        return ownedMissions;
    }

    public void setOwnedMissions(Set<Mission> ownedMissions) {
        this.ownedMissions = ownedMissions;
    }

    public Set<Prize> getTakenPrizes() {
        return takenPrizes;
    }

    public void addTakenPrize(Prize prize) {
        if (prize != null)
            this.takenPrizes.add(prize);
    }

    public int getSumPoints() {
        int sum = 0;
        for (Mission mission : this.missions) {
            if (mission.getStage().equals(Mission.STAGE.DONE.name()))
                sum += mission.getValue();
        }
        for (Prize prize : this.takenPrizes) {
            sum -= prize.getValue();
        }
        return sum;
    }

    public int getSumMissions() {
        int sum = 0;
        for (Mission mission : this.missions) {
            if (mission.getStage().equals(Mission.STAGE.ASSIGNED.name()))
                sum += 1;
        }
        return sum;
    }

    public Set<Prize> getOwnedPrizes() {
        return ownedPrizes;
    }

    public void setOwnedPrizes(Set<Prize> ownedPrizes) {
        this.ownedPrizes = ownedPrizes;
    }

    // getters - setters

    // interface methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return pass.getPassword();
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getActive();
    }

    // interface methods


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", active=" + active +
                ", pass=" + pass.getPassword() +
                ", roles=" + roles.stream().map(Role::getId).collect(Collectors.toList()) +
                ", children=" + children.stream().map(User::getId).collect(Collectors.toList()) +
                ", parents=" + parents.stream().map(User::getId).collect(Collectors.toList()) +
                ", missions=" + missions.stream().map(Mission::getId).collect(Collectors.toList()) +
                ", ownedMissions=" + ownedMissions.stream().map(Mission::getId).collect(Collectors.toList()) +
                ", ownedPrizes=" + ownedPrizes.stream().map(Prize::getId).collect(Collectors.toList()) +
                ", takenPrizes=" + takenPrizes.stream().map(Prize::getId).collect(Collectors.toList()) +
                ", sumPoints=" + sumPoints +
                ", sumMissions=" + sumMissions +
                '}';
    }
}
