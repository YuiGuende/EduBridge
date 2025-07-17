package service.video;

import model.course.courseContent.Video;

public interface IVideoService {

    public Video saveVideo(Video video);

    public Video update(Video video);

   public Video findById(Long id);

}
