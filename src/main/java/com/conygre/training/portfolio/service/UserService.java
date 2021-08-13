package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.pojo.StockWithPercent;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<User> getAllUsers();
    Double getNetWorth() throws IOException, ParseException;
    List<StockWithPercent> getUserGainersAndLosers();
}
