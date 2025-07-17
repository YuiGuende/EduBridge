package service.lesson;


import DAO.lesson.LessonDAOImpl;
import model.course.courseContent.Lesson;
import model.course.courseContent.Module;

import java.util.List;
import java.util.Optional;

public class LessonServiceImpl implements ILessonService {

    private final LessonDAOImpl lessonDAO = new LessonDAOImpl(Lesson.class);

    @Override
    public Lesson save(Lesson lesson) {
        return lessonDAO.save(lesson);
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        return lessonDAO.findByIdReturnOptional(id);
    }

    @Override
    public List<Lesson> findAll() {
        return lessonDAO.findAll();
    }

    @Override
    public Lesson update(Lesson lesson) {
        return lessonDAO.update(lesson);
    }

    @Override
    public void delete(Lesson lesson) {
        lessonDAO.delete(lesson);
    }

    @Override
    public void deleteById(Long id) {
        lessonDAO.deleteById(id);
    }

    @Override
    public List<Lesson> findByModule(Module module) {
        return lessonDAO.findByModule(module);
    }

    @Override
    public List<Lesson> findByModuleId(Long moduleId) {
        return lessonDAO.findByModuleId(moduleId);
    }

    @Override
    public List<Lesson> findByCourseId(Long courseId) {
        return lessonDAO.findByCourseId(courseId);
    }

    @Override
    public List<Lesson> findPreviewLessons() {
        return lessonDAO.findPreviewLessons();
    }

    @Override
    public List<Lesson> findByTitleContaining(String title) {
        return lessonDAO.findByTitleContaining(title);
    }

    @Override
    public Lesson findByIdWithLessonItems(Long id) {
        return lessonDAO.findByIdWithLessonItems(id);
    }

    @Override
    public long count() {
        return lessonDAO.count();
    }

    @Override
    public long countByModule(Module module) {
        return lessonDAO.countByModule(module);
    }

    @Override
    public boolean exists(Long id) {
        return lessonDAO.exists(id);
    }

    @Override
    public Lesson findNextLesson(Lesson currentLesson) {
        return lessonDAO.findNextLesson(currentLesson);
    }

    @Override
    public Lesson findPreviousLesson(Lesson currentLesson) {
        return lessonDAO.findPreviousLesson(currentLesson);
    }

    @Override
    public List<Lesson> findByDurationRange(int minDuration, int maxDuration) {
        return lessonDAO.findByDurationRange(minDuration, maxDuration);
    }
}
