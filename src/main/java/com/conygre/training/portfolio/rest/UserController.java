package com.conygre.training.portfolio.rest;

import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.service.UserService;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/networth")
    public Double getNetWorth() throws IOException, ParseException {return userService.getNetWorth(); }
}
