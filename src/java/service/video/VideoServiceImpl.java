package service.video;

import DAO.video.IVideoDAO;
import DAO.video.VideoDAOImpl;
import model.course.courseContent.Video;

public class VideoServiceImpl implements IVideoService {
    
    private final IVideoDAO videoDAO;
    
    public VideoServiceImpl() {
        this.videoDAO = new VideoDAOImpl();
    }
    
    @Override
    public Video saveVideo(Video video) {
        return videoDAO.save(video);
    }
    
    @Override
    public Video update(Video video) {
        return videoDAO.update(video);
    }
    
    @Override
    public Video findById(Long id) {
        return videoDAO.findById(id);
    }
}
