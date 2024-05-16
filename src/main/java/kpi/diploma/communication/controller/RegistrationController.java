package kpi.diploma.communication.controller;

import kpi.diploma.communication.data.UserRepository;
import kpi.diploma.communication.model.Role;
import kpi.diploma.communication.model.User;
import kpi.diploma.communication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, @RequestParam(name="password_repeat") String passwordRepeat, Model model){
        System.out.println(user);
        System.out.println(passwordRepeat);
        User userFromDB = userRepository.findById(user.getEmail()).orElse(null);
        if (userFromDB != null){
            model.addAttribute("error", "User exist");
            return "registration";
        }
        user.setRoles(Set.of( Role.TEACHER));
        model.addAttribute("errors", userService.validationData(user, passwordRepeat));
        userRepository.save(user);
        return "redirect:/login";
    }




}
