package com.conygre.training.portfolio.rest;

import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.pojo.StockWithPercent;
import com.conygre.training.portfolio.service.UserService;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/index/{userID}")
    public List<StockWithPercent> getUserGainersAndLosers(@PathVariable("userID") Integer userID) {
        return userService.getUserGainersAndLosers(userID);
    }

//    @PostMapping("/login")
//    public ResponseEntity<User> loginUser(@RequestBody User payload) {
//        System.out.println(payload);
//        System.out.println("testing");
//
//        return userService.loginUser(payload);
//    }
    @GetMapping("/allusers")
    public Collection<User> getUserGainersAndLosers() {
        return userService.getAllUsers();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/networth/{userId}")
    Double getNetWorth(@PathVariable("userID") Integer userID) throws IOException, ParseException {
        return userService.getNetWorth(userID);
    }

}
