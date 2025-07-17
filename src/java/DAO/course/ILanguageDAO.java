/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.course;

import java.util.List;
import java.util.Optional;
import model.course.Language;

/**
 *
 * @author LEGION
 */
public interface ILanguageDAO {

    Optional<Language> findByCode(String code);

    Optional<Language> findByName(String name);

    List<Language> findByNameContaining(String name);
}
