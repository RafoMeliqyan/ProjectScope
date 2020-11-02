package project.scope.am.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.scope.am.model.Type;
import project.scope.am.model.User;
import project.scope.am.repository.UserRepository;
import project.scope.am.security.CurrentUser;
import project.scope.am.service.ProjectService;
import project.scope.am.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Value("${file.upload.dir}")
    private String uploadDir;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String main() {
        return "home";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute User user, @RequestParam("image") MultipartFile file) throws IOException {
        Optional<User> byUsername = userService.findByUsername(user.getUsername());
        if (byUsername.isPresent()) {
            return "redirect:/";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String profilePic = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File image = new File(uploadDir, profilePic);
        file.transferTo(image);
        user.setProfile_picture(profilePic);
        userService.save(user);
        return "home";
    }

    @GetMapping("/successLogin")
    public String successLogin(Model model,@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/";
        }
        User user = currentUser.getUser();
        if (user.getType() == Type.TEAM_LEADER) {
            return "redirect:/teamLider";
        } else {
            model.addAttribute("user", user);
            return "team_member";
        }
    }

    @GetMapping("/teamLider")
    public String teamLider() {
        return "team_lider";
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/users")
    public String users(Model model,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "5") int size,
                        @AuthenticationPrincipal CurrentUser currentUser) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<User> all = userRepository.findAll(pageRequest);

        User user = currentUser.getUser();

        int totalPages = all.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("allUsers", all);
        model.addAttribute("user", user);
        return "users";
    }

    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImageWithMediaType(@RequestParam("name") String imageName) throws IOException {

        InputStream in = new FileInputStream(uploadDir + File.separator + imageName);
        return IOUtils.toByteArray(in);
    }

}
