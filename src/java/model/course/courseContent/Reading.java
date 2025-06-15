/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course.courseContent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author LEGION
 */
@Entity
@Table(name = "reading")
public class Reading extends LessonItem {

    private String content;

    public Reading() {
    }

    public Reading(String content, int index, String title, int estimatedDuration) {
        super(index, title, estimatedDuration);
        this.content = content;
    }

    public Reading(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Reading{" + "content=" + content + '}';
    }

}
