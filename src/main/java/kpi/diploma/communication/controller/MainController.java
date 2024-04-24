package kpi.diploma.communication.controller;

import kpi.diploma.communication.dto.UserDTO;
import kpi.diploma.communication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal UserDetails userDetails, Model model){
        model.addAttribute("name", userDetails.getUsername());
        return "main";

    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model){
        UserDTO userDTO = userService.getUserDTOById(userDetails.getUsername());
        UserDTO curator = userService.getCuratorForUser(userDTO.getGroup());
        List<UserDTO> teachers = userService.getTeachersForUser(userDTO.getGroup());

        model.addAttribute("user", userDTO);
        model.addAttribute("curator", curator);
        model.addAttribute("teachers", teachers);
        return "profile";


    }

}
