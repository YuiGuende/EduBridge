package service.lessonItem;


import DAO.lessonItem.ILessonItemDAO;
import DAO.lessonItem.LessonItemDAOImpl;
import model.course.courseContent.LessonItem;

import java.util.List;

public class LessonItemServiceImpl implements ILessonItemService {

    private final ILessonItemDAO lessonItemDAO;

    public LessonItemServiceImpl() {
        this.lessonItemDAO = new LessonItemDAOImpl(LessonItem.class);
    }

    @Override
    public LessonItem findById(Long id) {
        return lessonItemDAO.findById(id);
    }

    @Override
    public List<LessonItem> findAll() {
        return lessonItemDAO.findAll();
    }

    @Override
    public List<LessonItem> findByLessonId(Long lessonId) {
        return lessonItemDAO.findByLessonId(lessonId);
    }
}
