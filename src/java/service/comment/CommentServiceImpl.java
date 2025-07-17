package service.comment;

import DAO.comment.CommentDAOImpl;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import model.notification.*;
import model.course.Course;

import java.util.List;

import model.DTO.CommentListDTO;

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

    @Override
    public List<CommentListDTO> getCommentsList(String course, String user, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Comment> comments = commentDAO.findCommentsWithFilters(course, user, offset, pageSize);

        List<CommentListDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            CommentListDTO dto = new CommentListDTO();
            dto.setId(comment.getId());
            dto.setUser(comment.getUser());
            dto.setCourse(comment.getCourse());
            dto.setContent(comment.getContent());
            dto.setCreatedAt(Date.from(comment.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()));
            commentDTOs.add(dto);
        }

        return commentDTOs;
    }

    @Override
    public int getCommentsCount(String course, String user) {
        return commentDAO.countCommentsWithFilters(course, user);
    }

    @Override
    public List<Comment> findByCourse(Long courseId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
