package DAO.user;

import java.util.List;
import model.user.Instructor;

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
}
