package project.scope.am.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.scope.am.model.Project;
import project.scope.am.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> findByName(String name) {
        return projectRepository.findAllByNameContaining(name);
    }

    public List<Project> findByMember(int id) {
        return projectRepository.findAllByMember_Id(id);
    }

    public void save(Project project) {
        projectRepository.save(project);
    }

    public void deleteById(int id) {
        projectRepository.deleteById(id);
    }

}
