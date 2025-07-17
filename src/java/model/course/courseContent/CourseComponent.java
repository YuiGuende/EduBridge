/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course.courseContent;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 *
 * @author LEGION
 */
@MappedSuperclass
public abstract class CourseComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "[index]")
    protected int index;
    @Column(name = "title", length = 1000, columnDefinition = "nvarchar(255)")
    protected String title;
    @Column(name = "description", length = 1000, columnDefinition = "nvarchar(255)")
    protected String description;

    public CourseComponent() {
    }

//    public CourseComponent(Long id, int index, String title) {
//        this.id = id;
//        this.index = index;
//        this.title = title;
//    }
//
//    public CourseComponent(int index, String title) {
//        this.index = index;
//        this.title = title;
//    }

    public CourseComponent(int index, String title, String description) {
        this.index = index;
        this.title = title;
        this.description = description;
    }

    public CourseComponent(Long id, int index, String title, String description) {
        this.id = id;
        this.index = index;
        this.title = title;
        this.description = description;
    }
    

    // getters và setters
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setTitle(String title) {
        this.title = title;
    }
}
