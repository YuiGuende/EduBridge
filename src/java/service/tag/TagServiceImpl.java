/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.tag;

import DAO.course.TagDAOImpl;
import java.util.List;
import model.course.Tag;
import model.course.TagType;

/**
 *
 * @author DELL
 */
public class TagServiceImpl implements ITagService{
    private final TagDAOImpl tagDao;

    public TagServiceImpl() {
        this.tagDao = new TagDAOImpl(Tag.class);
    }

    @Override
    public List<Tag> getTagsByType(TagType type) {
        return tagDao.findTagsByType(type);
    }

    @Override
    public Tag getTagByNameAndType(String name, TagType type) {
        return tagDao.findTagByNameAndType(name, type);
    }

    @Override
    public Tag save(Tag tag) {
        return tagDao.save(tag);
    }

}
