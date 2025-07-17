/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.language;

import DAO.course.ILanguageDAO;
import DAO.course.LanguageDAO;
import java.util.List;
import java.util.Optional;
import model.course.Language;

public class LanguageImpl implements ILanguage {

    private final LanguageDAO languageDAO;

    public LanguageImpl() {
        languageDAO = new LanguageDAO(Language.class);
    }
    

    @Override
    public List<Language> findALl() {
        return languageDAO.findAll();
    }

    @Override
    public Optional<Language> findById(Long id) {
        return languageDAO.findByIdReturnOptional(id);
    }

}
