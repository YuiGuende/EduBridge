package model.course;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import model.course.courseContent.Subtitle;

@Entity
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "code", length = 10, unique = true)
    private String code; // e.g., "en", "vi", "fr"

    @Column(name = "native_name", length = 100)
    private String nativeName; // e.g., "English", "Tiếng Việt", "Français"

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "languages", fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "language", fetch = FetchType.LAZY)
    private List<Subtitle> subtitles = new ArrayList<>();

    // Constructors
    public Language() {
    }

    public Language(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Language(Long id, String name, String code, String nativeName) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.nativeName = nativeName;
    }

    public Language(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    
    

    public Language(String name, String code, String nativeName) {
        this.name = name;
        this.code = code;
        this.nativeName = nativeName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses != null ? courses : new ArrayList<>();
    }

    public List<Subtitle> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(List<Subtitle> subtitles) {
        this.subtitles = subtitles != null ? subtitles : new ArrayList<>();
    }

    // For backward compatibility
    public String getText() {
        return name;
    }

    public void setText(String text) {
        this.name = text;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", nativeName='" + nativeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language)) return false;
        Language language = (Language) o;
        return code != null && code.equals(language.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}