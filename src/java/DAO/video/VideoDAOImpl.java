package DAO.video;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import model.course.courseContent.Video;

public class VideoDAOImpl extends GenericDAO<Video> implements IVideoDAO {

    public VideoDAOImpl() {
        super(Video.class);
    }

}
