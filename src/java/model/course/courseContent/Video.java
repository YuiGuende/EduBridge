/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course.courseContent;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author LEGION
 */
@Entity
@Table(name = "video")
public class Video extends LessonItem {

    private String videoURL;
    @OneToMany(mappedBy = "video")
    private List<Subtitle> subtitle;

    public Video() {
    }

    public Video(String videoURL, List<Subtitle> subtitle) {
        this.videoURL = videoURL;
        this.subtitle = subtitle;
    }

    public Video(String videoURL, int index, String title, int estimatedDuration) {
        super(index, title, estimatedDuration);
        this.videoURL = videoURL;
        this.subtitle = new ArrayList<>();
    }

    public Video(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public List<Subtitle> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(List<Subtitle> subtitle) {
        this.subtitle = subtitle;
    }

}
