package kpi.diploma.communication.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(@AuthenticationPrincipal UserDetails userDetails, Model model){
        model.addAttribute("name", userDetails.getUsername());
        return "main";

    }

}
