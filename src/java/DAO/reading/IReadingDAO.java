package DAO.reading;


import model.course.courseContent.Reading;

public interface IReadingDAO {
    Reading save(Reading reading);
    Reading update(Reading reading);
    Reading findById(Long id);
}
