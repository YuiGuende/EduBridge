/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Course {

    private int id;
    private String title;
    private String headLine;
    private String description;
    private String[] requirement;
    private String[] courseFor;
    private String[] learningOutcomes;
    private Language language;
    private List<Tag> tags =new ArrayList<>();
    private String thumbnailUrl;
    private boolean paid;
    private CourseStatus status;
    private LocalDateTime publishedTime;
    private LocalDateTime lastUpdate;

    public Course() {
    }

    public Course(
            String title,
            String headLine,
            String description,
            String[] requirement,
            String[] courseFor,
            String[] learningOutcomes,
            Language language,
            String thumbnailUrl,
            CourseStatus status) {
        this.title = title;
        this.headLine = headLine;
        this.description = description;
        this.requirement = requirement;
        this.courseFor = courseFor;
        this.learningOutcomes = learningOutcomes;
        this.language = language;
        this.thumbnailUrl = thumbnailUrl;
        this.paid = false;
        this.status = status;
        this.lastUpdate = LocalDateTime.now();
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
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

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getRequirement() {
        return requirement;
    }

    public String getStringRequirement() {
        return String.join("|||", this.requirement);
    }

    public static String[] processStringToArray(String str) {
        return str.split("\\|\\|\\|");
    }

    public void setRequirement(String[] requirement) {
        this.requirement = requirement;
    }

    public String[] getCourseFor() {
        return courseFor;
    }

    public String getStringCourseFor() {
        return String.join("|||", this.courseFor);
    }

    public void setCourseFor(String[] courseFor) {
        this.courseFor = courseFor;
    }

    public String[] getLearningOutcomes() {
        return learningOutcomes;
    }

    public String getStringLearningOutcomes() {
        return String.join("|||", this.learningOutcomes);
    }

  

    public void setLearningOutcomes(String[] learningOutcomes) {
        this.learningOutcomes = learningOutcomes;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public LocalDateTime getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(LocalDateTime publishedTime) {
        this.publishedTime = publishedTime;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", title=" + title + ", headLine=" + headLine + ", description=" + description + ", requirement=" + requirement + ", courseFor=" + courseFor + ", learningOutcomes=" + learningOutcomes + ", language=" + language + ", thumbnailUrl=" + thumbnailUrl + ", paid=" + paid + ", status=" + status + ", publishedTime=" + publishedTime + ", lastUpdate=" + lastUpdate + '}';
    }

}
