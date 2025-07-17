package DAO.user;
import java.util.List;
import model.user.Learner;

public interface ILearnerDAO {
    Learner findById(Long id);
    Learner save(Learner learner);
    Learner update(Learner learner);
    void deleteById(Long id);
    List<Learner> findAll();
    
    // Admin panel specific methods
    List<Learner> findLearnersWithFilters(String name, String email, int offset, int limit);
    int countLearnersWithFilters(String name, String email);
    int countActiveLearners();
}
