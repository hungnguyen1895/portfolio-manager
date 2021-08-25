package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.pojo.StockWithPercent;
import net.minidev.json.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserService {
    Collection<User> getAllUsers();
    Double getNetWorth(Integer userID) throws IOException, ParseException;
    ResponseEntity<User> loginUser(@RequestBody User payload);
    List<StockWithPercent> getUserGainersAndLosers(Integer userID);
}
