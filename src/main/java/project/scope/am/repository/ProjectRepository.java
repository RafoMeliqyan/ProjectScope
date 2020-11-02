package project.scope.am.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.scope.am.model.Project;

import java.util.Date;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer> {

    List<Project> findAllByNameContaining(String name);
    List<Project> findAllByMember_Id(Integer id);
    List<Project> findAllByDateIsStartingWithAndDeadlineIsEndingWith(Date date, Date deadline);

}
