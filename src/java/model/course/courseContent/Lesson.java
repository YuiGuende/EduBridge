/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course.courseContent;

import java.io.Serializable;
import java.util.ArrayList;
import model.course.courseContent.CourseComponent;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author LEGION
 */
@Entity
@Table(name = "lesson")
public class Lesson extends CourseComponent implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LessonItem> lessonItems = new ArrayList<>();

    public Lesson() {
    }

    public Lesson(Long id, int index, String title) {
        super(id, index, title);
    }

    public Lesson(Module module, int index, String title) {
        super(index, title);
        this.module = module;
    }

    public Lesson(List<LessonItem> lessonItems) {
        this.lessonItems = lessonItems;
    }

    public Lesson(List<LessonItem> lessonItems, Long id, int index, String title) {
        super(id, index, title);
        this.lessonItems = lessonItems;
    }

    public List<LessonItem> getLessonItems() {
        return lessonItems;
    }

    public void setLessonItems(List<LessonItem> lessonItems) {
        this.lessonItems = lessonItems;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public int getEstimatedDuration() {
        return lessonItems.stream()
                .mapToInt(item -> item.getEstimatedDuration())
                .sum();
    }

}
