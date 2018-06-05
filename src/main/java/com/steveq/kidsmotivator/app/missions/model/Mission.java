package com.steveq.kidsmotivator.app.missions.model;

import com.steveq.kidsmotivator.app.auth.model.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "missions_seq")
//    @SequenceGenerator(name = "missions_seq", sequenceName = "missions_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "mission_title")
    private String title;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "mission_description")
    private String description;

    @NotNull
    @Size(min = 3, max = 10)
    @Column(name = "mission_stage")
    private String stage = STAGE.OPEN.name();

    @Min(value = 1)
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

    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "{validation.date.pattern}")
    @Transient
    private String dateFormat;

    @Transient
    private int assignedId;

    public Mission() {}

    public Mission(String title,
                   String description,
                   String stage,
                   int value,
                   User assignedKid,
                   User owner,
                   Date deadline,
                   String dateFormat,
                   int assignedId) {
        this.title = title;
        this.description = description;
        this.stage = stage;
        this.value = value;
        this.assignedKid = assignedKid;
        this.owner = owner;
        this.deadline = deadline;
        this.dateFormat = dateFormat;
        this.assignedId = assignedId;
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
        if (this.deadline != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime ld = new Timestamp(this.deadline.getTime()).toLocalDateTime();
            return dtf.format(ld);
        }

        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(dateFormat, dtf);
        this.deadline = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.dateFormat = dateFormat;
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
