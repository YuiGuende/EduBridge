package service.comment;


import model.notification.*;
import model.course.Course;
import java.util.List;

public interface ICommentService {

    Comment save(Comment comment);

    Comment update(Comment comment);

    void delete(Comment comment);

    void deleteById(Long id);

    Comment findById(Long id);

    List<Comment> findAll();

    List<Comment> findByCourse(Course course);

    List<Comment> findByCourseId(Long courseId);

    long countByCourse(Long courseId);
}
