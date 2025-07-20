package service.user;

import DAO.user.IInstructorDAO;
import DAO.user.IUserDAO;
import DAO.user.InstructorDAOImpl;
import DAO.user.UserDAOImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.DTO.InstructorListDTO;
import model.user.Instructor;
import model.user.User;
import model.user.UserStatus;

public class InstructorServiceImpl implements IInstructorService {

    private final IInstructorDAO instructorDAO;
    private final IUserDAO userDAO = new UserDAOImpl(User.class);

    public InstructorServiceImpl() {
        this.instructorDAO = new InstructorDAOImpl(Instructor.class);
    }

    @Override
    public Instructor createInstructor(Instructor instructor) {
        return instructorDAO.save(instructor);
    }

    @Override
    public Instructor getInstructorById(Long id) {
        return instructorDAO.findById(id);
    }

    @Override
    public Instructor updateInstructor(User user, Instructor instructor) {
        // Ensure the user object within the instructor is updated
        instructor.setUser(user);
        return instructorDAO.save(instructor);
    }

    @Override
    public void deleteInstructor(Long id) {
        instructorDAO.deleteById(id);
    }

    @Override
    public List<Instructor> getInstructorsWithPagination(int page, int size) {
        return instructorDAO.findInstructorsWithPagination(page, size);
    }

    @Override
    public long getTotalInstructors() {
        return instructorDAO.countInstructors();
    }

    @Override
    public List<Instructor> getInstructorsWithFilters(String name, String email, String specialization, int offset, int limit) {
        return instructorDAO.findInstructorsWithFilters(name, email, specialization, offset, limit);
    }

    @Override
    public int countInstructorsWithFilters(String name, String email, String specialization) {
        return instructorDAO.countInstructorsWithFilters(name, email, specialization);
    }

    @Override
    public int countActiveInstructors() {
        return instructorDAO.countActiveInstructors();
    }

    @Override
    public List<Instructor> getInstructorsBySpecialization(String specialization) {
        return instructorDAO.findBySpecialization(specialization);
    }

    @Override
    public Instructor createInstructor(User user, Instructor instructor) {
        user.setRole("INSTRUCTOR"); // Ensure role is set
        instructor.setUser(user); // Link user to instructor
        return instructorDAO.save(instructor);
    }

    @Override
    public List<InstructorListDTO> getAllInstructorsByStatus(UserStatus status) {
        return instructorDAO.findAllInstructors(status).stream()
                .map(instructor -> new InstructorListDTO(instructor.getUser())) // Chuyển Instructor sang User để tạo DTO
                .collect(Collectors.toList());
    }

    @Override
    public void updateInstructorStatus(Long id, UserStatus status) {
        instructorDAO.updateInstructorStatus(id, status);
    }

    @Override
    public List<InstructorListDTO> getInstructorsWithPaginationAndStatus(int page, int size, UserStatus status) {
        return instructorDAO.findInstructorsWithPagination(page, size, status).stream()
                .map(instructor -> new InstructorListDTO(instructor.getUser())) // Chuyển Instructor sang User để tạo DTO
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalInstructorsByStatus(UserStatus status) {
        return instructorDAO.countInstructors(status);
    }

    @Override
    public Instructor saveInstructor(Instructor instructor) {
        return instructorDAO.save(instructor);
    }
}
