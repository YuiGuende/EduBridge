/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.course;

import agentAi.GroqService;
import java.util.List;
import java.util.stream.Collectors;
import DAO.course.CourseDAOImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import model.DTO.CourseListDTO;
import model.DTO.PendingApprovalDTO;
import model.DTO.RecentActivityDTO;
import model.course.Course;
import model.course.CourseStatus;
import model.course.Tag;
import model.notification.Comment;

public class CourseServiceImpl implements CourseService {

    private final GroqService groqService = new GroqService();

    private final CourseDAOImpl courseDAO;

    public CourseServiceImpl() {
        this.courseDAO = new CourseDAOImpl(Course.class);
    }

    @Override
    public void addCourse(Course course) {
        courseDAO.save(course);
    }

    @Override
    public List<Comment> getSortedCommentsByCourse(Long courseId) {
        return courseDAO.sortByCreated(courseId);
    }

    @Override
    public Course findCourse(Long id) {
        return courseDAO.findById(id);
    }

    @Override
    public String processUserMessage(String message, boolean showMore) throws Exception {
        // 1. AI sinh SQL
        String sql = groqService.generateSQLFromUserQuery(message, showMore);

        // 2. Gọi DB để lấy danh sách khóa học
        List<Course> courseList = courseDAO.findCoursesByDynamicSql(sql);
        if (courseList.isEmpty()) {
            System.out.println("list is null");
        } else {
            System.out.println("ALl title found by AI");
            for (Course course : courseList) {
                System.out.println(course.getTitle());
            }
        }

        // 3. Gộp context lại để tạo prompt cuối
        String context = buildCourseContext(courseList);

        // 4. Gửi prompt tới AI để sinh phản hồi cuối
        String finalPrompt = groqService.buildSystemPromptWithCourses(context);
        return groqService.callGroqWithPrompt(finalPrompt, message);
    }

    private String buildCourseContext(List<Course> courses) {
        if (courses.isEmpty()) {
            return "Không tìm thấy khóa học phù hợp.";
        }

        return courses.stream().map(course -> String.format(
                "- 📚 %s (%s): %s tuần - %s\nMô tả: %s",
                course.getTitle(),
                course.isPaid() ? "Trả phí" : "Miễn phí",
                course.getEstimatedTime(),
                course.getPrimaryLanguage() != null ? course.getPrimaryLanguage().getName() : "Không rõ",
                course.getHeadline()
        )).collect(Collectors.joining("\n\n"));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    @Override
    public List<Course> getCoursesOfInstructorByStatus(long instructorID, String status, int offset, int limit) {
        return courseDAO.findCoursesByStatus(instructorID, status, offset, limit);
    }

    @Override
    public int countCoursesOfInstructor(long instructorID, String status, String keyword) {
        return courseDAO.countCourses(instructorID, status, keyword);
    }

    @Override
    public List<Course> getCourseByKeywordAndStatus(long instructorID, String keyword, String status, int offset, int limit) {
        return courseDAO.findCoursesByKeywordAndStatus(instructorID, keyword, status, offset, limit);
    }


    @Override
    public List<Course> sortCourses(List<Course> courses, String sort) {
        if (courses == null) {
            return Collections.emptyList();
        }

        switch (sort != null ? sort : "") {
            case "az":
                courses.sort(Comparator.comparing(Course::getTitle, String.CASE_INSENSITIVE_ORDER));
                break;
            case "za":
                courses.sort(Comparator.comparing(Course::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());
                break;
            case "newest":
                courses.sort(Comparator.comparing(Course::getPublishedTime, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "oldest":
                courses.sort(Comparator.comparing(Course::getPublishedTime, Comparator.nullsLast(Comparator.naturalOrder())));
                break;
//            case "popular":
//                courses.sort(Comparator.comparing(Course::getEnrollCount, Comparator.nullsLast(Comparator.reverseOrder())));
//                break;
//            case "rating":
//                courses.sort(Comparator.comparing(Course::getRating, Comparator.nullsLast(Comparator.reverseOrder())));
//                break;
            default:
                courses.sort(Comparator.comparing(Course::getId));
                break;
        }

        return courses;
    }

    public static void main(String[] args) {
        CourseDAOImpl dao = new CourseDAOImpl(Course.class);
        List<Course> list = dao.findCoursesByStatus(Long.valueOf("2"), "", 0, 5);
        System.out.println(list);
    }

    @Override
    public List<Course> findRelatedCourses(Course currentCourse, int limit) {
        if (currentCourse.getTags() != null && !currentCourse.getTags().isEmpty()) {
            String firstTag = currentCourse.getTags().get(0).getName();
            return courseDAO.findByTag(firstTag, limit);
        }

        // Fallback: dùng phương thức mới
        return courseDAO.findCoursesLimited(limit);
    }

    @Override
    public List<Course> findCoursesLimited(int limit) {
        return courseDAO.findCoursesLimited(limit);
    }

    @Override
    public List<Course> getTrendingCourses() {
        return courseDAO.getTrendingCourses();
    }

    @Override
    public List<Course> getCoursesByTag(Tag tag) {
        return courseDAO.getCoursesByTag(tag);
    }

    @Override
    public int getTotalCoursesCount() {
        return (int) courseDAO.count();
    }

    @Override
    public List<RecentActivityDTO> getRecentActivities(int limit) {
        List<RecentActivityDTO> activities = new ArrayList<>();

        // Get recent course activities
        List<Course> recentCourses = courseDAO.findRecentCourses(limit);
        for (Course course : recentCourses) {
            RecentActivityDTO activity = new RecentActivityDTO();
            activity.setDescription("Course created: " + course.getTitle());
            activity.setUserName(course.getCreatedBy().getUser().getFullname());
            activity.setCreatedAt(Date.from(course.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant()));
            activity.setStatus(course.getStatus().toString());
            activity.setStatusColor(getStatusColor(course.getStatus()));
            activities.add(activity);
        }

        return activities;
    }

    @Override
    public List<PendingApprovalDTO> getPendingApprovals(int limit) {
        List<PendingApprovalDTO> approvals = new ArrayList<>();

        List<Course> pendingCourses = courseDAO.findByStatus(CourseStatus.REQUESTING, limit);
        for (Course course : pendingCourses) {
            PendingApprovalDTO approval = new PendingApprovalDTO();
            approval.setCourseId(course.getId());
            approval.setCourseTitle(course.getTitle());
            approval.setInstructorName(course.getCreatedBy().getUser().getFullname());
            approvals.add(approval);
        }

        return approvals;
    }

    @Override
    public List<CourseListDTO> getCoursesList(String title, String status, String instructor, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Course> courses = courseDAO.findCoursesWithFilters(title, status, instructor, offset, pageSize);

        List<CourseListDTO> courseDTOs = new ArrayList<>();
        for (Course course : courses) {
            CourseListDTO dto = new CourseListDTO();
            dto.setId(course.getId());
            dto.setTitle(course.getTitle());
            dto.setHeadline(course.getHeadline());
            dto.setThumbnailUrl(course.getThumbnailUrl());
            dto.setStatus(course.getStatus());
            dto.setCreatedDate(Date.from(course.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant()));
            dto.setInstructors(course.getInstructors());
            dto.setRate(course.getRate());
            dto.setCreatedBy(course.getCreatedBy().getUser().getFullname());
            dto.setCreatedById(course.getCreatedBy().getUser().getId());
            courseDTOs.add(dto);
        }

        return courseDTOs;
    }

    @Override
    public int getCoursesCount(String title, String status, String instructor) {
        return courseDAO.countCoursesWithFilters(title, status, instructor);
    }

    @Override
    public Course findById(Long id) {
        return courseDAO.findById(id);
    }

    @Override
    public boolean updateCourseStatus(Long courseId, CourseStatus status, String reason) {
        try {
            Course course = courseDAO.findById(courseId);
            if (course != null) {
                course.setStatus(status);
                if (reason != null && !reason.isEmpty()) {
//                    course.setRejectReason(reason);
                }
                courseDAO.update(course);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Course save(Course course) {
        return courseDAO.save(course);
    }

    @Override
    public Course update(Course course) {
        return courseDAO.update(course);
    }

    @Override
    public void delete(Long id) {
        courseDAO.deleteById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseDAO.findAll();
    }

    @Override
    public List<Course> findByStatus(CourseStatus status) {
        return courseDAO.findByStatus(status);
    }

    @Override
    public List<Course> findByInstructor(Long instructorId) {
        return courseDAO.findByInstructor(instructorId);
    }

    private String getStatusColor(CourseStatus status) {
        switch (status) {
            case PUBLIC:
                return "success";
            case REQUESTING:
                return "warning";
            case DRAFT:
                return "info";
            case ARCHIVE:
                return "secondary";
            default:
                return "primary";
        }
    }

}
