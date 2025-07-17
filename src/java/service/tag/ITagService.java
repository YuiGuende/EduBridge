/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.tag;

import java.util.List;
import model.course.Tag;
import model.course.TagType;

/**
 *
 * @author DELL
 */
public interface ITagService {

    List<Tag> getTagsByType(TagType type);

    Tag getTagByNameAndType(String name, TagType type);
    
    Tag save(Tag tag);
    
}
