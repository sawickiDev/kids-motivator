package com.steveq.kidsmotivator.app.prizes.model;

import com.steveq.kidsmotivator.app.auth.model.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "prizes_table")
public class Prize {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prizes_seq")
//    @SequenceGenerator(name = "prizes_seq", sequenceName = "prizes_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prize_id")
    private int id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "prize_name")
    private String name;

    @NotNull
    @Min(value = 1)
    @Column(name = "prize_value")
    private int value;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "assignee")
    private User assignee;

    public Prize() {}

    public Prize(String name,
                 int value,
                 User owner,
                 User assignee) {
        this.name = name;
        this.value = value;
        this.owner = owner;
        this.assignee = assignee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        return "Prize{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", owner=" + owner +
                ", assignee=" + assignee +
                '}';
    }
}
