/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.LearnerLessonItem;

import DAO.learnerLessonItem.LearnerLessonItemDAO;
import java.util.List;
import model.course.LearningStatus;
import model.course.courseContent.LearnerLessonItem;

/**
 *
 * @author DELL
 */
public class LearnerLessonItemServiceImpl implements ILearnerLessonItemService{
    private final LearnerLessonItemDAO lliDao = new LearnerLessonItemDAO(LearnerLessonItem.class);

    @Override
    public LearnerLessonItem getByLearnerAndLessonItem(Long learnerId, Long lessonItemId) {
        return lliDao.findByLearnerAndLessonItem(learnerId, lessonItemId);
    }

    @Override
    public List<LearnerLessonItem> getByLearnerAndCourse(Long learnerId, Long courseId) {
        return lliDao.findByLearnerAndCourse(learnerId, courseId);
    }

    @Override
    public LearnerLessonItem save(LearnerLessonItem lli) {
        return lliDao.save(lli);
    }

    @Override
    public void updateProgress(Long learnerId, Long lessonItemId, LearningStatus status, Double progress) {
        lliDao.updateLearningStatus(learnerId, lessonItemId, status, progress);
    }

    @Override
    public double calculateCourseProgress(Long learnerId, Long courseId) {
        long totalItems = lliDao.countLessonItemsInCourse(courseId);
        long completedItems = lliDao.countCompletedLessonItems(learnerId, courseId);

        if (totalItems == 0) {
            return 0.0;
        }
        return (completedItems * 100.0) / totalItems;
    }
    
    public static void main(String[] args) {
        // Tạo Service
        LearnerLessonItemServiceImpl lliService = new LearnerLessonItemServiceImpl();

        // Giả sử learnerId & courseId test
        Long learnerId = 1L;
        Long courseId = 1L;

        // Tính tiến độ
        double progress = lliService.calculateCourseProgress(Long.valueOf(1), Long.valueOf(1));

        System.out.printf("Learner %d đã hoàn thành %.2f%% khoá học %d%n", learnerId, progress, courseId);
    }
    
}
