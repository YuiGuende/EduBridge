/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.language;

import java.util.List;
import java.util.Optional;
import model.course.Language;

/**
 *
 * @author LEGION
 */
public interface ILanguage {

    public List<Language> findALl();
     public Optional<Language> findById(Long id);
}
