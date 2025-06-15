/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course.courseContent;

/**
 *
 * @author LEGION
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // hoặc SINGLE_TABLE hay TABLE_PER_CLASS tùy ý
@Table(name = "lesson_item")
public abstract class LessonItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lesson_index") // hoặc một tên khác như position, sortOrder
    private int index;

    private String title;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    private int estimatedDuration;

    public LessonItem(int index, Long id, String title, int estimatedDuration) {
        this.index = index;
        this.id = id;
        this.title = title;
        this.estimatedDuration = estimatedDuration;
    }

    public LessonItem(int index, String title, int estimatedDuration) {
        this.index = index;
        this.title = title;
        this.estimatedDuration = estimatedDuration;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public LessonItem() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "LessonItem{" + "index=" + index + ", id=" + id + ", title=" + title + '}';
    }

}
