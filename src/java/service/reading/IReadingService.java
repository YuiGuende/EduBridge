package service.reading;


import model.course.courseContent.Reading;

public interface IReadingService {
    Reading saveReading(Reading reading);
    Reading update(Reading reading);
    Reading findById(Long id);
}
