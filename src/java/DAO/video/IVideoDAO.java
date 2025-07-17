package DAO.video;

import model.course.courseContent.Video;




public interface IVideoDAO {
    Video save(Video video);
    Video update(Video video);
    Video findById(Long id);
}
