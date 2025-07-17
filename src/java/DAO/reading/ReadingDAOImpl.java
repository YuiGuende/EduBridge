package DAO.reading;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import model.course.courseContent.Reading;

public class ReadingDAOImpl extends GenericDAO<Reading> implements IReadingDAO {

    public ReadingDAOImpl() {
        super(Reading.class);
    }

}
