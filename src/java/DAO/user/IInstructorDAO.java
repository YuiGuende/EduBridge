package DAO.user;

import java.util.List;
import java.util.Optional;
import model.user.Instructor;
import model.user.User;
import model.user.UserStatus;

public interface IInstructorDAO {

    Instructor findById(Long id);

    Instructor save(Instructor instructor);

    Instructor update(Instructor instructor);

    void deleteById(Long id);

    List<Instructor> findAll();

    // Admin panel specific methods
    List<Instructor> findInstructorsWithFilters(String name, String email, String specialization, int offset, int limit);

    int countInstructorsWithFilters(String name, String email, String specialization);

    int countActiveInstructors();

    List<Instructor> findBySpecialization(String specialization);

    List<Instructor> findInstructorsWithPagination(int page, int size);

    long countInstructors();
    
    void updateInstructorStatus(Long id, UserStatus status); // Cập nhật trạng thái giảng viên
    List<Instructor> findInstructorsWithPagination(int page, int size, UserStatus status); // Phân trang giảng viên theo trạng thái
    long countInstructors(UserStatus status); // Đếm giảng viên theo trạng thái
    
    List<Instructor> findInstructorsWithFilters(String name, String email, int offset, int limit, UserStatus status); // Lọc theo trạng thái
    int countInstructorsWithFilters(String name, String email, UserStatus status); // Đếm theo trạng thái
     List<Instructor> findAllInstructors(UserStatus status);
}
