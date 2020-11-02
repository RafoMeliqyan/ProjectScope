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
import project.scope.am.model.Log;
import project.scope.am.model.Project;
import project.scope.am.model.User;
import project.scope.am.repository.LogRepository;
import project.scope.am.repository.ProjectRepository;
import project.scope.am.security.CurrentUser;
import project.scope.am.service.LogService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class LogController {

    private final ProjectRepository projectRepository;
    private final LogService logService;
    private final LogRepository logRepository;

    @GetMapping("/logs")
    public String logs(Model model,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @AuthenticationPrincipal CurrentUser currentUser) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Log> all = logRepository.findAll(pageRequest);

        int totalPages = all.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        User user = currentUser.getUser();
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
    public String addLog(@ModelAttribute Log log) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(log.getDate());
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(format);
        log.setDate(date);
        logService.save(log);
        return "redirect:/logs";
    }

    @GetMapping("/logs/delete")
    public String deleteLog(@RequestParam("id") int id) {
        logService.deleteById(id);
        return "redirect:/logs";
    }

}
