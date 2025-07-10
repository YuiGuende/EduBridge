/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import model.course.Course;
import model.user.User;

/**
 *
 * @author LEGION
 */
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "target_id", nullable = false)
    private ReportTarget target;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ReportType type;

    @Column(name = "seen", nullable = false)
    private boolean seen = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Report() {
    }

    public Report(User user, ReportTarget target, String content, ReportType type) {
        this.user = user;
        this.target = target;
        this.content = content;
        this.type = type;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReportTarget getTarget() {
        return target;
    }

    public void setTarget(ReportTarget target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Report{"
                + "id=" + id
                + ", user=" + user.getEmail()
                + ", targetId=" + target.getId()
                + ", content='" + content + '\''
                + ", type=" + type
                + ", seen=" + seen
                + ", createdAt=" + createdAt
                + '}';
    }
}
