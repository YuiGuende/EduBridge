package service.reading;

import DAO.reading.IReadingDAO;
import DAO.reading.ReadingDAOImpl;
import model.course.courseContent.Reading;

public class ReadingServiceImpl implements IReadingService {

    private final IReadingDAO readingDAO = new ReadingDAOImpl();

    @Override
    public Reading saveReading(Reading reading) {
        return readingDAO.save(reading);
    }

    @Override
    public Reading update(Reading reading) {
        return readingDAO.update(reading);
    }

    @Override
    public Reading findById(Long id) {
        return readingDAO.findById(id);
    }
}
