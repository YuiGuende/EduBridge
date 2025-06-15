/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course.courseContent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LEGION
 */
public class Quiz extends LessonItem {

    private List<Question> questions = new ArrayList<>();
    private int numberOfDisplayedQuestion;

    public Quiz() {
    }

    

    public Quiz(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getNumberOfDisplayedQuestion() {
        return numberOfDisplayedQuestion;
    }

    public void setNumberOfDisplayedQuestion(int numberOfDisplayedQuestion) {
        this.numberOfDisplayedQuestion = numberOfDisplayedQuestion;
    }

    @Override
    public String toString() {
        return "Quiz{" + "questions=" + questions + '}';
    }

}
