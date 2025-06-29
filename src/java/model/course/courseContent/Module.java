/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course.courseContent;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.course.Course;


@Entity
@Table(name = "modules")
public class Module extends CourseComponent {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("index ASC")
    private List<Lesson> lessons = new ArrayList<>();

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "estimated_duration")
    private Integer estimatedDuration; // in minutes

    @Column(name = "is_preview", nullable = false)
    private boolean preview = false;

    // Constructors
    public Module() {
        super();
    }

//    public Module(String title, int index) {
//        super();
//        this.setTitle(title);
//        this.setCourseComponentIndex(index);
//    }
//
//    public Module(String title, int index, String description) {
//        this(title, index);
//        this.description = description;
//    }
    public Module(String description, Integer estimatedDuration, int index, String title) {
        super(index, title);
        this.description = description;
        this.estimatedDuration = estimatedDuration;
    }

    public Module(Course course, String description, Integer estimatedDuration, int index, String title) {
        super(index, title);
        this.course = course;
        this.description = description;
        this.estimatedDuration = estimatedDuration;
    }
    

    // Helper methods
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setModule(this);
    }
    

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
        lesson.setModule(null);
    }

    public int getTotalLessons() {
        return lessons.size();
    }

    public int getTotalDuration() {
        return lessons.stream()
                .mapToInt(lesson -> lesson.getEstimatedDuration())
                .sum();
    }

    // Getters and Setters
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons != null ? lessons : new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public boolean isPreview() {
        return preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }
    

    @Override
    public String toString() {
        return "Module{"
                + "id=" + getId()
                + ", title='" + getTitle() + '\''
                + ", index=" + getIndex()
                + ", lessonsCount=" + lessons.size()
                + ", course=" + (course != null ? course.getTitle() : null)
                + '}';
    }
}
