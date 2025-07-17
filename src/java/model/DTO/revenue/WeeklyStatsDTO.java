/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.DTO.revenue;

import java.util.List;

/**
 *
 * @author DELL
 */
public class WeeklyStatsDTO {

    private int newStudents;
    private int courseSales;

    public WeeklyStatsDTO(int newStudents, int courseSales) {
        this.newStudents = newStudents;
        this.courseSales = courseSales;
    }

    public int getNewStudents() {
        return newStudents;
    }

    public int getCourseSales() {
        return courseSales;
    }

    @Override
    public String toString() {
        return "WeeklyStatsDTO{"
                + "newStudents=" + newStudents
                + ", courseSales=" + courseSales
                + '}';
    }
}
