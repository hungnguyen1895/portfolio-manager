package com.conygre.training.portfolio.rest;

import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.pojo.StockWithPercent;
import com.conygre.training.portfolio.service.UserService;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/index")
    public List<StockWithPercent> getUserGainersAndLosers() {
        return userService.getUserGainersAndLosers();
    }


}
