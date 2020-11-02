package project.scope.am.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.scope.am.model.Project;
import project.scope.am.model.User;
import project.scope.am.repository.ProjectRepository;
import project.scope.am.repository.UserRepository;
import project.scope.am.security.CurrentUser;
import project.scope.am.service.ProjectService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final UserRepository userRepository;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;

    @GetMapping("/newProjectPage")
    public String newProjectPage(Model model) {
        List<User> allUsers = userRepository.findAll();
        model.addAttribute("allUsers", allUsers);
        return "newProject";
    }

    @GetMapping("/userProjects")
    public String userProjects(@RequestParam("id") int id,Model model,@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        List<Project> allByMember_id = projectService.findByMember(id);
        model.addAttribute("allByMember_id", allByMember_id);
        model.addAttribute("user", user);
        return "memberProjects";
    }

    @GetMapping("/projects")
    public String projects(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size,
                           @AuthenticationPrincipal CurrentUser currentUser) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Project> allProjects = projectRepository.findAll(pageRequest);

        User user = currentUser.getUser();

        int totalPages = allProjects.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("allProjects", allProjects);
        model.addAttribute("user", user);
        return "projects";
    }

    @PostMapping("/project/add")
    public String addProject(@ModelAttribute Project project) {
        project.setDate(LocalDate.now());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(project.getDeadline());
        projectService.save(project);
        return "redirect:/projects";
    }

    @GetMapping("/project/delete")
    public String deleteProject(@RequestParam("id") int id) {
        projectService.deleteById(id);
        return "redirect:/projects";
    }

}
