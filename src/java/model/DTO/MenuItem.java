package model.DTO;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author GoniperXComputer
 */
public class MenuItem {
        private String type; // Video, Reading, quiz
    private String title;
    private String subtitle;
    private int duration; // in minutes
    private String iconClass;
    private boolean active;
    
    public MenuItem(){
        
    }

    public MenuItem(String type, String title, String subtitle, int duration, String iconClass, boolean active) {
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.duration = duration;
        this.iconClass = iconClass;
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "MenuItem{" + "type=" + type + ", title=" + title + ", subtitle=" + subtitle + ", duration=" + duration + ", iconClass=" + iconClass + ", active=" + active + '}';
    }
    
    
    
    
}