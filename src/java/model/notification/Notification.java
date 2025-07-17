package model.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import model.user.User;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;


    @Column(nullable = false)
    private String message;

   
    @Column(name = "is_read", nullable = false)
    private boolean read = false;

    private String targetUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    public Notification() {
    }

    public Notification(User receiver, User sender, String message, String targetUrl, NotificationType type) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.targetUrl = targetUrl;
        this.type = type;
    }

    public Notification(Long id, User receiver, User sender, String message, String targetUrl, NotificationType type) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.targetUrl = targetUrl;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setIsRead(boolean isRead) {
        this.read = isRead;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification{" + "id=" + id + ", receiver=" + receiver + ", sender=" + sender + ", message=" + message + ", isRead=" + read + ", targetUrl=" + targetUrl + ", createdAt=" + createdAt + '}';
    }

}
