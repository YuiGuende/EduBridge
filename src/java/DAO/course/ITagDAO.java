/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.course;

import java.util.List;
import model.course.Tag;
import model.course.TagType;

/**
 *
 * @author DELL
 */
public interface ITagDAO {

    List<Tag> findTagsByType(TagType type);

    Tag findTagByNameAndType(String name, TagType type);

}
