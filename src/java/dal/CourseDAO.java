package dal;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import model.course.Course;
import model.course.CourseStatus;
import model.course.Language;
import model.course.Tag;

public class CourseDAO {

    private Connection conn;
    private final LanguageDAO languageDAO = new LanguageDAO();

    public CourseDAO() {
        try {
            conn = DBContext.getConnection(); // Giả định bạn có DBUtil quản lý kết nối
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Convert LocalDateTime <-> Timestamp
    private Timestamp toTimestamp(LocalDateTime time) {
        return (time == null) ? null : Timestamp.valueOf(time);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return (timestamp == null) ? null : timestamp.toLocalDateTime();
    }

    // ----------- 1. findAll() ----------------
    public List<Course> findAll() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM Course";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToCourse(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----------- 2. findById() ----------------
    public Course findById(int id) {
        String sql = "SELECT * FROM Course WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCourse(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ----------- 3. findByTag() ----------------
    public List<Course> findByTag(String tagName) {
        List<Course> list = new ArrayList<>();
        String sql = """
            SELECT c.*
            FROM Course c
            JOIN Course_Tag ct ON c.id = ct.course_id
            JOIN Tag t ON t.id = ct.tag_id
            WHERE t.name = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tagName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToCourse(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----------- 4. save() ----------------
    public void save(Course course) {
        String sql = """
            INSERT INTO Course (title, headline, description, requirement, course_for, learning_outcomes,
                                language_id, url_thumbnail, is_paid, status, published_time, last_update)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getHeadLine());
            ps.setString(3, course.getDescription());
            ps.setString(4, course.getStringRequirement());
            ps.setString(5, course.getStringCourseFor());
            ps.setString(6, course.getStringLearningOutcomes());
            Language l = languageDAO.findByLanguage(course.getLanguage().getLanguage());
            ps.setObject(7, (l != null) ? l.getId() : null);
            ps.setString(8, course.getThumbnailUrl());
            ps.setBoolean(9, course.isPaid());
            ps.setString(10, course.getStatus().name().toLowerCase());
            ps.setTimestamp(11, toTimestamp(course.getPublishedTime()));
            ps.setTimestamp(12, toTimestamp(course.getLastUpdate()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    course.setId(rs.getInt(1));
                }
            }

            // Optional: insert course tags
            insertCourseTags(course);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------- 5. update() ----------------
    public void update(Course course) {
        String sql = """
            UPDATE Course SET title = ?, headline = ?, description = ?, requirement = ?, course_for = ?, learning_outcomes = ?,
                              language_id = ?, url_thumbnail = ?, is_paid = ?, status = ?, published_time = ?, last_update = ?
            WHERE id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getHeadLine());
            ps.setString(3, course.getDescription());
            ps.setString(4, course.getStringRequirement());
            ps.setString(5, course.getStringCourseFor());
            ps.setString(6, course.getStringLearningOutcomes());
            ps.setObject(7, (course.getLanguage() != null) ? course.getLanguage().getId() : null);
            ps.setString(8, course.getThumbnailUrl());
            ps.setBoolean(9, course.isPaid());
            ps.setString(10, course.getStatus().name().toLowerCase());
            ps.setTimestamp(11, toTimestamp(course.getPublishedTime()));
            ps.setTimestamp(12, toTimestamp(course.getLastUpdate()));
            ps.setInt(13, course.getId());

            ps.executeUpdate();

            // Optional: update tags if needed
            // deleteOldTags(course.getId());
            // insertCourseTags(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------- Helper: Mapping ResultSet -> Course ----------------
    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {

        LanguageDAO languageDAO= new LanguageDAO();
        Course c = new Course();
        c.setId(rs.getInt("id"));
        c.setTitle(rs.getString("title"));
        c.setHeadLine(rs.getString("headline"));
        c.setDescription(rs.getString("description"));

        c.setRequirement(Course.processStringToArray(rs.getString("requirement")));
        c.setCourseFor(Course.processStringToArray(rs.getString("course_for")));
        c.setLearningOutcomes(Course.processStringToArray(rs.getString("learning_outcomes")));

        // Giả định có class LanguageDAO để lấy Language theo ID
        int langId = rs.getInt("language_id");
        if (!rs.wasNull()) {
            c.setLanguage(languageDAO.findById(langId)); // hoặc dùng languageDAO.findById(langId)
        }

        c.setThumbnailUrl(rs.getString("url_thumbnail"));
        c.setPaid(rs.getBoolean("is_paid"));

        String statusStr = rs.getString("status");
        c.setStatus(CourseStatus.valueOf(statusStr.toUpperCase()));

        c.setPublishedTime(toLocalDateTime(rs.getTimestamp("published_time")));
        c.setLastUpdate(toLocalDateTime(rs.getTimestamp("last_update")));

        return c;
    }

    // ---------- Optional: insert tag mapping ----------------
    private void insertCourseTags(Course course) {
        String sql = "INSERT INTO Course_Tag(course_id, tag_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Tag tag : course.getTags()) {
                ps.setInt(1, course.getId());
                ps.setInt(2, tag.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Course> findCourses(int instructorID, String status, String keyword, String sort, int offset, int limit) {
        List<Course> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT c.* FROM Course c " +
            "INNER JOIN Course_Instructor ci ON c.id = ci.course_id " +
            "WHERE ci.instructor_id = ? "
        );

        if (status != null && !status.equals("all")) {
            sql.append("AND c.status = ? ");
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND c.title LIKE ? ");
        }

        switch (sort) {
            case "az": sql.append("ORDER BY c.title ASC "); break;
            case "za": sql.append("ORDER BY c.title DESC "); break;
            case "newest": sql.append("ORDER BY c.published_time DESC "); break;
            case "oldest": sql.append("ORDER BY c.published_time ASC "); break;
            case "popular": sql.append("ORDER BY c.enrollCount DESC "); break;
            case "rating": sql.append("ORDER BY c.rating DESC "); break;
            default: sql.append("ORDER BY c.id ASC "); break;
        }

        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            ps.setInt(paramIndex++, instructorID);

            if (status != null && !status.equals("all")) {
                ps.setString(paramIndex++, status);
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword + "%");
            }

            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countCourses(int instructorID, String status, String keyword) {
        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(*) FROM Course c " +
            "INNER JOIN Course_Instructor ci ON c.id = ci.course_id " +
            "WHERE ci.instructor_id = ? "
        );

        if (status != null && !status.equals("all")) {
            sql.append("AND c.status = ? ");
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND c.title LIKE ? ");
        }

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            ps.setInt(paramIndex++, instructorID);

            if (status != null && !status.equals("all")) {
                ps.setString(paramIndex++, status);
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
