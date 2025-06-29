package model.course;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.user.Instructor;
import model.course.courseContent.Module;

@Entity
@Table(name = "courses")
public class Course {
//annotation

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255, columnDefinition = "nvarchar(255)")
    private String title;

    @Column(name = "headline", nullable = false, length = 500, columnDefinition = "nvarchar(255)")
    private String headline;

    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description;

    @ElementCollection
    @CollectionTable(name = "course_requirements",
            joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "requirement", length = 1000, columnDefinition = "nvarchar(255)")
    private List<String> requirements = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "course_target_audience",
            joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "target_audience", length = 1000, columnDefinition = "nvarchar(255)")
    private List<String> courseFor = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "course_learning_outcomes",
            joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "learning_outcome", length = 1000, columnDefinition = "nvarchar(255)")
    private List<String> learningOutcomes = new ArrayList<>();

    // Thay đổi từ ManyToOne thành ManyToMany
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "course_languages",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<Language> languages = new HashSet<>();

    // Thêm trường primaryLanguage để lưu ngôn ngữ chính của khóa học
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "primary_language_id")
    private Language primaryLanguage;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "course_tags",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Course_Instructor",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id"))
    private List<Instructor> instructors = new ArrayList<>();
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("index ASC")
    private List<Module> modules = new ArrayList<>();

    @Column(name = "thumbnail_url", length = 500, columnDefinition = "nvarchar(255)")
    private String thumbnailUrl;

    @Column(name = "is_paid", nullable = false)
    private boolean paid = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CourseStatus status = CourseStatus.DRAFT;

    @Column(name = "published_time")
    private LocalDateTime publishedTime;
    @Column(name = "estimated_time")
    private int estimatedTime;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @Column(name = "created_by", length = 100, columnDefinition = "nvarchar(255)")
    private String createdBy;

    @Column(name = "updated_by", length = 100, columnDefinition = "nvarchar(255)")
    private String updatedBy;

    @Version
    private Long version;

    // Constructors
    public Course() {
    }

    public Course(String title, String headline, String description,
            Language primaryLanguage, String thumbnailUrl, CourseStatus status) {
        this.title = title;
        this.headline = headline;
        this.description = description;
        this.primaryLanguage = primaryLanguage;
        if (primaryLanguage != null) {
            this.languages.add(primaryLanguage);
        }
        this.thumbnailUrl = thumbnailUrl;
        this.status = status;
        this.paid = false;
    }

    public Course(String title, String headline, String description, String thumbnailUrl, CourseStatus status) {
        this.title = title;
        this.headline = headline;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.status = status;
        this.paid = false;
        this.languages = new HashSet<>();
    }

    public Course(Long id, String title, String headline, String description, String thumbnailUrl, CourseStatus courseStatus) {
        this.id = id;
        this.title = title;
        this.headline = headline;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.status = courseStatus;
        this.paid = false;
        this.languages = new HashSet<>();
    }

    public Course(Long id, String title, String headline, String description, String thumbnailUrl, CourseStatus courseStatus, int estimatedTime) {
        this.id = id;
        this.title = title;
        this.headline = headline;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.status = courseStatus;
        this.paid = false;
        this.languages = new HashSet<>();
        this.estimatedTime = estimatedTime;
    }

    public Course(String title, String headline, String description, String thumbnailUrl, CourseStatus courseStatus, int estimatedTime) {
        this.title = title;
        this.headline = headline;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.status = courseStatus;
        this.paid = false;
        this.languages = new HashSet<>();
        this.estimatedTime = estimatedTime;
    }

    // JPA Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        lastUpdate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }

    public void addLanguage(Language language) {
        if (language != null) {
            languages.add(language);
            language.getCourses().add(this);

            // Nếu chưa có primaryLanguage, đặt language này làm primaryLanguage
            if (primaryLanguage == null) {
                primaryLanguage = language;
            }
        }
    }

    public void removeLanguage(Language language) {
        if (language != null && languages.contains(language)) {
            languages.remove(language);
            language.getCourses().remove(this);

            // Nếu xóa primaryLanguage, đặt primaryLanguage thành null hoặc language khác nếu có
            if (primaryLanguage != null && primaryLanguage.equals(language)) {
                primaryLanguage = languages.isEmpty() ? null : languages.iterator().next();
            }
        }
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getCourses().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getCourses().remove(this);
    }

    public void addModule(Module module) {
        modules.add(module);
        module.setCourse(this);
    }

    public void removeModule(Module module) {
        modules.remove(module);
        module.setCourse(null);
    }

    public void addRequirement(String requirement) {
        if (requirement != null && !requirement.trim().isEmpty()) {
            requirements.add(requirement.trim());
        }
    }

    public void addCourseFor(String targetAudience) {
        if (targetAudience != null && !targetAudience.trim().isEmpty()) {
            courseFor.add(targetAudience.trim());
        }
    }

    public void addLearningOutcome(String outcome) {
        if (outcome != null && !outcome.trim().isEmpty()) {
            learningOutcomes.add(outcome.trim());
        }
    }

    // Utility methods for string conversion (for backward compatibility)
    public String getRequirementsAsString() {
        return String.join("|||", requirements);
    }

    public void setRequirementsFromString(String requirementsStr) {
        requirements.clear();
        if (requirementsStr != null && !requirementsStr.trim().isEmpty()) {
            String[] parts = requirementsStr.split("\\|\\|\\|");
            for (String part : parts) {
                addRequirement(part);
            }
        }
    }

    public String getCourseForAsString() {
        return String.join("|||", courseFor);
    }

    public void setCourseForFromString(String courseForStr) {
        courseFor.clear();
        if (courseForStr != null && !courseForStr.trim().isEmpty()) {
            String[] parts = courseForStr.split("\\|\\|\\|");
            for (String part : parts) {
                addCourseFor(part);
            }
        }
    }

    public String getLearningOutcomesAsString() {
        return String.join("|||", learningOutcomes);
    }

    public void setLearningOutcomesFromString(String outcomesStr) {
        learningOutcomes.clear();
        if (outcomesStr != null && !outcomesStr.trim().isEmpty()) {
            String[] parts = outcomesStr.split("\\|\\|\\|");
            for (String part : parts) {
                addLearningOutcome(part);
            }
        }
    }

    // Business logic methods
    public void publish() {
        if (status == CourseStatus.DRAFT || status == CourseStatus.REQUESTING) {
            status = CourseStatus.PUBLIC;
            publishedTime = LocalDateTime.now();
        }
    }

    public void archive() {
        status = CourseStatus.ARCHIVE;
    }

    public boolean isPublished() {
        return status == CourseStatus.PUBLIC;
    }

    public int getTotalModules() {
        return modules.size();
    }

    public int getTotalLessons() {
        return modules.stream()
                .mapToInt(module -> module.getLessons().size())
                .sum();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements != null ? requirements : new ArrayList<>();
    }

    public List<String> getCourseFor() {
        return courseFor;
    }

    public void setCourseFor(List<String> courseFor) {
        this.courseFor = courseFor != null ? courseFor : new ArrayList<>();
    }

    public List<String> getLearningOutcomes() {
        return learningOutcomes;
    }

    public void setLearningOutcomes(List<String> learningOutcomes) {
        this.learningOutcomes = learningOutcomes != null ? learningOutcomes : new ArrayList<>();
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages != null ? languages : new HashSet<>();
    }

    public Language getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(Language primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
//        if (primaryLanguage != null) {
//            addLanguage(primaryLanguage);
//        }
    }

    // Phương thức tương thích ngược để duy trì code cũ
    public Language getLanguage() {
        return primaryLanguage;
    }

    public void setLanguage(Language language) {
        setPrimaryLanguage(language);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags != null ? tags : new ArrayList<>();
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules != null ? modules : new ArrayList<>();
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public LocalDateTime getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(LocalDateTime publishedTime) {
        this.publishedTime = publishedTime;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Course{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", headline='" + headline + '\''
                + ", primaryLanguage=" + (primaryLanguage != null ? primaryLanguage.getName() : null)
                + ", languages=" + languages.size()
                + ", status=" + status
                + ", paid=" + paid
                + ", modulesCount=" + modules.size()
                + ", tagsCount=" + tags.size()
                + '}';
    }
}
