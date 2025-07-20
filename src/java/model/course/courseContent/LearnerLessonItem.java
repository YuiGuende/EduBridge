/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course.courseContent;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import model.course.LearningStatus;
import model.user.Learner;

/**
 *
 * @author DELL
 */
@Entity
@Table(
        name = "Learner_LessonItem",
        uniqueConstraints = @UniqueConstraint(columnNames = {"learner_id", "lesson_item_id"})
)
public class LearnerLessonItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "learning_status")
    private LearningStatus learningStatus;

    @Column(name = "progress_percent")
    private Double progressPercent;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // Relation: nếu cần truy ngược LessonItem
    @ManyToOne
    @JoinColumn(name = "lesson_item_id", insertable = false, updatable = false)
    private LessonItem lessonItem;

    // Relation: nếu cần truy ngược Learner
    @ManyToOne
    @JoinColumn(name = "learner_id", insertable = false, updatable = false)
    private Learner learner;

    public LearnerLessonItem() {
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LearningStatus getLearningStatus() {
        return learningStatus;
    }

    public void setLearningStatus(LearningStatus learningStatus) {
        this.learningStatus = learningStatus;
    }

    public Double getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(Double progressPercent) {
        this.progressPercent = progressPercent;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LessonItem getLessonItem() {
        return lessonItem;
    }

    public void setLessonItem(LessonItem lessonItem) {
        this.lessonItem = lessonItem;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    @Override
    public String toString() {
        return "LearnerLessonItem{" + "id=" + id + ", learningStatus=" + learningStatus + ", progressPercent=" + progressPercent + ", startedAt=" + startedAt + ", completedAt=" + completedAt + ", lessonItem=" + lessonItem + ", learner=" + learner + '}';
    }

}
