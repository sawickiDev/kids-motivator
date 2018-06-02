package com.steveq.kidsmotivator.app.missions.model;

import com.steveq.kidsmotivator.app.persistence.model.User;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "missions_table")
public class Mission {

    public enum STAGE {
        OPEN,
        ASSIGNED,
        DONE,
        OVERDUE
    };

    @Id
    @Column(name = "mission_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "missions_seq")
    @SequenceGenerator(name = "missions_seq", sequenceName = "missions_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "mission_title")
    private String title;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "mission_description")
    private String description;

    @Column(name = "mission_stage")
    private String stage;

    @Column(name = "mission_value")
    private int value;

    @ManyToOne
    @JoinColumn(name = "assignee")
    private User assignedKid;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @Column (name = "mission_deadline")
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @Transient
    private String dateFormat;

    @Transient
    private int assignedId;

    public Mission() {}

    public Mission(@NotNull @Size(min = 2, max = 50) String title, @NotNull @Size(min = 2, max = 100) String description, String stage, User assignedKid, User owner, Date deadline, String dateFormat) {
        this.title = title;
        this.description = description;
        this.stage = stage;
        this.assignedKid = assignedKid;
        this.owner = owner;
        this.deadline = deadline;
        this.dateFormat = dateFormat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public User getAssignedKid() {
        return assignedKid;
    }

    public void setAssignedKid(User assignedKid) {
        this.assignedKid = assignedKid;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-dd");
        System.out.println("DEADLINE :: " + this.deadline);
        if (this.deadline != null)
            return sdf.format(this.deadline);
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-dd");
        this.dateFormat = dateFormat;
        try {
            this.deadline = sdf.parse(this.dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAssignedId() {
        return assignedId;
    }

    public void setAssignedId(int assignedId) {
        this.assignedId = assignedId;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", stage='" + stage + '\'' +
                ", value=" + value +
                ", assignedKid=" + assignedKid +
                ", owner=" + owner +
                ", deadline=" + deadline +
                ", dateFormat='" + dateFormat + '\'' +
                ", assignedId=" + assignedId +
                '}';
    }
}
