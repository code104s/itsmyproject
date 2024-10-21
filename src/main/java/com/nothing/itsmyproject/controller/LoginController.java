package com.nothing.itsmyproject.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/login")
    public String login() {

        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/forgot")
    public String forgot() {
        return "forgot";
    }

    @RequestMapping("/reset")
    public String reset() {
        return "reset";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "logout";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/user")
    public String user() {
        return "user";
    }

    @RequestMapping("/403")
    public String error403() {
        return "403";
    }
}
