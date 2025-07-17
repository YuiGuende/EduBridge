/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.user;

import java.util.List;
import java.util.Optional;
import model.DTO.InstructorListDTO;
import model.DTO.LearnerListDTO;
import model.user.Instructor;
import model.user.InstructorBankInfo;
import model.user.Learner;
import model.user.User;

/**
 *
 * @author LEGION
 */
public interface IUserService {

    Optional<User> login(String username, String password);

    void signup(User user, Learner learner);


    User findById(Long id);

    
    void signupForInstructor(User user, Instructor instructor, InstructorBankInfo bankInfo);
    

    boolean isEmailExists(String emails);

    User findUserByEmail(String email);
    
    Double getAverageRateByInstructor(Long instructorId);

    int getActiveInstructorsCount();

    int getTotalStudentsCount();

    // Instructor management
    List<InstructorListDTO> getInstructorsList(String name, String email, String specialization, int page, int pageSize);

    int getInstructorsCount(String name, String email, String specialization);

    Instructor findInstructorById(Long id);

    boolean updateUserRole(Long userId, String role);

    boolean updateInstructor(Long instructorId, String fullname, String email, String specialization,
            int experienceYears, String educationLevel, String linkedinProfile, String bio);

    // Learner management
    List<LearnerListDTO> getLearnersList(String name, String email, int page, int pageSize);

    int getLearnersCount(String name, String email);

    Learner findLearnerById(Long id);

    // User operations
    User save(User user);

    User update(User user);

    void delete(Long id);

    List<User> findAll();

    User findByEmail(String email);
}
