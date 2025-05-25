/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

/**
 *
 * @author LEGION
 */
public class Language {

    private int id;
    private String language;

    public Language() {
    }

    public Language(int id, String language) {
        this.id = id;
        this.language = language;
    }

    public Language(String language) {
        this.language = language;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
