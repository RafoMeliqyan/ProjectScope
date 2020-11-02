package project.scope.am.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.scope.am.model.Log;
import project.scope.am.model.Project;
import project.scope.am.model.User;
import project.scope.am.repository.LogRepository;
import project.scope.am.repository.ProjectRepository;
import project.scope.am.security.CurrentUser;
import project.scope.am.service.LogService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LogController {

    private final ProjectRepository projectRepository;
    private final LogService logService;

    @GetMapping("/logs")
    public String logs(Model model,@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        List<Log> all = logService.findAll();
        model.addAttribute("logs", all);
        model.addAttribute("user", user);
        return "logs";
    }

    @GetMapping("/addLogPage")
    public String addLogPage(Model model) {
        List<Project> all = projectRepository.findAll();
        model.addAttribute("allProjects", all);
        return "addLog";
    }

    @PostMapping("/logs/add")
    public String addLog(@ModelAttribute Log log) {
        log.setDate(LocalDate.now());
        logService.save(log);
        return "redirect:/logs";
    }

    @GetMapping("/logs/delete")
    public String deleteLog(@RequestParam("id") int id) {
        logService.deleteById(id);
        return "redirect:/logs";
    }

}
