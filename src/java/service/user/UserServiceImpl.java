/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.user;

import DAO.GenericDAO;
import DAO.user.IInstructorDAO;
import DAO.user.ILearnerDAO;
import DAO.user.IUserDAO;
import DAO.user.InstructorDAOImpl;
import DAO.user.LearnerDAOImpl;
import DAO.user.UserDAOImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.DTO.InstructorListDTO;
import model.DTO.LearnerListDTO;
import model.user.Instructor;
import model.user.InstructorBankInfo;
import model.user.InstructorBankInfo;
import model.user.Learner;
import model.user.User;

/**
 *
 * @author LEGION
 */
public class UserServiceImpl implements IUserService {

    private IUserDAO userDAO;
    private ILearnerDAO learnerDAO;
    private final IInstructorDAO instructorDAO= new InstructorDAOImpl(Instructor.class);
//    private final GenericDAO<Instructor> instructorDAO = new GenericDAO<>(Instructor.class);
    private final GenericDAO<InstructorBankInfo> bankInfoDAO = new GenericDAO<>(InstructorBankInfo.class);

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl(User.class);
        this.learnerDAO = new LearnerDAOImpl(Learner.class);
    }

    @Override
    public Optional<User> login(String username, String password) {
        return userDAO.findByEmailAndPassword(username, password);
    }

    @Override
    public void signup(User user, Learner learner) {
        userDAO.insert(user);
        learner.setId(user.getId());
        learner.setUser(user);
        user.setLearner(learner);

    }

    @Override
    public void signupForInstructor(User user, Instructor instructor, InstructorBankInfo bankInfo) {
        user.setInstructor(instructor);
        instructor.setUser(user);

        instructor.setInstructorBankInfo(bankInfo);
        bankInfo.setInstructor(instructor);
        userDAO.insert(user);

    }

    @Override
    public boolean isEmailExists(String emails) {
        return userDAO.isEmailExists(emails);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public int getActiveInstructorsCount() {
        return instructorDAO.countActiveInstructors();
    }

    @Override
    public int getTotalStudentsCount() {
        return learnerDAO.countActiveLearners();
    }

    @Override
    public List<InstructorListDTO> getInstructorsList(String name, String email, String specialization, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Instructor> instructors = instructorDAO.findInstructorsWithFilters(name, email, specialization, offset, pageSize);

        List<InstructorListDTO> instructorDTOs = new ArrayList<>();
        for (Instructor instructor : instructors) {
            InstructorListDTO dto = new InstructorListDTO();
            dto.setId(instructor.getId());
            dto.setUser(instructor.getUser());
            dto.setSpecialization(instructor.getSpecialization());
            dto.setExperienceYears(instructor.getExperienceYears());
            dto.setEducationLevel(instructor.getEducationLevel());
            dto.setLinkedinProfile(instructor.getLinkedinProfile());
            dto.setBio(instructor.getBio());
            dto.setAvatarUrl(instructor.getAvatarUrl());
            dto.setCoursesCreated(instructor.getCoursesCreated());
            instructorDTOs.add(dto);
        }

        return instructorDTOs;
    }

    @Override
    public int getInstructorsCount(String name, String email, String specialization) {
        return instructorDAO.countInstructorsWithFilters(name, email, specialization);
    }

    @Override
    public Instructor findInstructorById(Long id) {
        return instructorDAO.findById(id);
    }

    @Override
    public boolean updateUserRole(Long userId, String role) {
        try {
            User user = userDAO.findById(userId);
            if (user != null) {
                user.setRole(role);
                userDAO.update(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateInstructor(Long instructorId, String fullname, String email, String specialization,
            int experienceYears, String educationLevel, String linkedinProfile, String bio) {
        try {
            Instructor instructor = instructorDAO.findById(instructorId);
            if (instructor != null) {
                // Update user info
                User user = instructor.getUser();
                user.setFullname(fullname);
                user.setEmail(email);
                userDAO.update(user);

                // Update instructor info
                instructor.setSpecialization(specialization);
                instructor.setExperienceYears(experienceYears);
                instructor.setEducationLevel(educationLevel);
                instructor.setLinkedinProfile(linkedinProfile);
                instructor.setBio(bio);
                instructorDAO.update(instructor);

                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<LearnerListDTO> getLearnersList(String name, String email, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Learner> learners = learnerDAO.findLearnersWithFilters(name, email, offset, pageSize);

        List<LearnerListDTO> learnerDTOs = new ArrayList<>();
        for (Learner learner : learners) {
            LearnerListDTO dto = new LearnerListDTO();
            dto.setId(learner.getId());
            dto.setUser(learner.getUser());
            dto.setEducationLevel(learner.getEducationLevel());
            dto.setBio(learner.getBio());
            dto.setCourseLearnerList(learner.getCourseLearnerList());
            learnerDTOs.add(dto);
        }

        return learnerDTOs;
    }

    @Override
    public int getLearnersCount(String name, String email) {
        return learnerDAO.countLearnersWithFilters(name, email);
    }

    @Override
    public Learner findLearnerById(Long id) {
        return learnerDAO.findById(id);
    }

    @Override
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    public User update(User user) {
        return userDAO.update(user);
    }

    @Override
    public void delete(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public Double getAverageRateByInstructor(Long instructorId) {
        return userDAO.getAverageRateByInstructor(instructorId);
    }

}
