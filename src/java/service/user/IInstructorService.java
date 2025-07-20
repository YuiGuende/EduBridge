package service.user;

import java.util.List;
import java.util.Optional;
import model.DTO.InstructorListDTO;
import model.user.Instructor;
import model.user.User;
import model.user.UserStatus;

public interface IInstructorService {

    Instructor saveInstructor(Instructor instructor);

    Instructor createInstructor(User user, Instructor instructor);

    Instructor createInstructor(Instructor instructor);

    Instructor getInstructorById(Long id);

    Instructor updateInstructor(User user, Instructor instructor);

    void deleteInstructor(Long id);

    // Methods for pagination
    List<Instructor> getInstructorsWithPagination(int page, int size);

    long getTotalInstructors();

    // Existing methods with filters (if still needed)
    List<Instructor> getInstructorsWithFilters(String name, String email, String specialization, int offset, int limit);

    int countInstructorsWithFilters(String name, String email, String specialization);

    int countActiveInstructors();

    List<Instructor> getInstructorsBySpecialization(String specialization);

    void updateInstructorStatus(Long id, UserStatus status); // Cập nhật trạng thái giảng viên

    List<InstructorListDTO> getInstructorsWithPaginationAndStatus(int page, int size, UserStatus status); // Phân trang giảng viên theo trạng thái

    long getTotalInstructorsByStatus(UserStatus status); // Đếm giảng viên theo trạng thái

    List<InstructorListDTO> getAllInstructorsByStatus(UserStatus status); // Lấy giảng viên theo trạng thái
}
