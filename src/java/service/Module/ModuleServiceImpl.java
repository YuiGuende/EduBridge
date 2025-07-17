/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.Module;

import DAO.module.IModuleDAO;
import DAO.module.ModuleDAOImpl;
import java.util.List;
import java.util.Optional;
import model.course.Course;
import model.course.courseContent.Module;

/**
 *
 * @author LEGION
 */
public class ModuleServiceImpl implements IModuleService {

    private final ModuleDAOImpl moduleDAO;

    public ModuleServiceImpl() {
        this.moduleDAO = new ModuleDAOImpl(model.course.courseContent.Module.class);
    }

    @Override
    public Module save(Module module) {
        return moduleDAO.save(module);
    }

    @Override
    public Module findById(Long id) {
        return moduleDAO.findById(id);
    }

    @Override
    public List<Module> findAll() {
        return moduleDAO.findAll();
    }

    @Override
    public Module update(Module module) {
        return moduleDAO.update(module);
    }

    @Override
    public void delete(Module module) {
        moduleDAO.delete(module);
    }

    @Override
    public void deleteById(Long id) {
        moduleDAO.deleteById(id);
    }

    // Specialized Queries
    @Override
    public List<Module> findByCourse(Course course) {
        return moduleDAO.findByCourse(course);
    }

    @Override
    public List<Module> findByCourseId(Long courseId) {
        return moduleDAO.findByCourseId(courseId);
    }

    @Override
    public List<Module> findPreviewModules() {
        return moduleDAO.findPreviewModules();
    }

    @Override
    public List<Module> findByTitleContaining(String title) {
        return moduleDAO.findByTitleContaining(title);
    }

    @Override
    public Module findByIdWithLessons(Long id) {
        return moduleDAO.findByIdWithLessons(id);
    }

    @Override
    public Module findNextModule(Module currentModule) {
        return moduleDAO.findNextModule(currentModule);
    }

    @Override
    public Module findPreviousModule(Module currentModule) {
        return moduleDAO.findPreviousModule(currentModule);
    }

    // Utility Methods
    @Override
    public long countByCourse(Course course) {
        return moduleDAO.countByCourse(course);
    }

    @Override
    public boolean exists(Long id) {
        return moduleDAO.exists(id);
    }

    @Override
    public Module findByIndexAndCourseId(int index, Long courseId) {
        return moduleDAO.findByIndexAndCourseId(index, courseId);
    }

    @Override
    public long count() {
        return moduleDAO.count();
    }
}
