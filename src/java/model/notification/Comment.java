package model.notification;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import model.course.Course;
import model.user.User;

@Entity
@Table(name = "comments")
public class Comment extends ReportTarget {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    // Ng??i g?i comment (h?c vi�n)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    private int rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public Comment() {
    }

    public Comment(User user, Course course, String content) {
        this.user = user;
        this.course = course;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public Comment(User user, Course course, String content, Long id) {
        super(id);
        this.user = user;
        this.course = course;
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }  
    

    // Getters and setters
  
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public String toString() {
        return "Comment{"  + ", user=" + user + ", course=" + course + ", content=" + content + ", createdAt=" + createdAt + '}';
    }
    
    
}
