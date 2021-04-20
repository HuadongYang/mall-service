package com.mal.service.api;

import com.mal.service.domain.Mall;
import com.mal.service.domain.User;
import com.mal.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public User login( @RequestParam String account,  @RequestParam String password) {
        return userService.login(account, password);
    }


}
