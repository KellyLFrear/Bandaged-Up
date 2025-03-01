package com.example.demo.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password) {
        return loginService.loginUser(username, password);
    }
}
