package com.blogwebsite.blogwebapp.controller;

import com.blogwebsite.blogwebapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/user-login")
    public String showMyLoginPage() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

    @GetMapping("/create-new-user")
    public String showCreateNewLogin(Model model) {
        model.addAttribute("user", new User());
        return "newUser";
    }

    @GetMapping("/create-new-admin")
    public String showCreateNewLoginAdmin(Model model) {
        model.addAttribute("user", new User());
        return "newAdmin";
    }
}
