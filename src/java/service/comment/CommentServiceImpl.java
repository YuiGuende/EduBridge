package service.comment;


import DAO.comment.CommentDAOImpl;
import model.notification.*;
import model.course.Course;

import java.util.List;

public class CommentServiceImpl implements ICommentService {

    private final CommentDAOImpl commentDAO;

    public CommentServiceImpl() {
        this.commentDAO = new CommentDAOImpl();
    }

    @Override
    public Comment save(Comment comment) {
        return commentDAO.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return commentDAO.update(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentDAO.delete(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentDAO.deleteById(id);
    }

    @Override
    public Comment findById(Long id) {
        return commentDAO.findById(id);
    }

    @Override
    public List<Comment> findAll() {
        return commentDAO.findAll();
    }

    @Override
    public List<Comment> findByCourse(Course course) {
        return commentDAO.findByCourse(course);
    }

    @Override
    public List<Comment> findByCourseId(Long courseId) {
        return commentDAO.findByCourseId(courseId);
    }

    @Override
    public long countByCourse(Long courseId) {
        return commentDAO.countByCourse(courseId);
    }
}
