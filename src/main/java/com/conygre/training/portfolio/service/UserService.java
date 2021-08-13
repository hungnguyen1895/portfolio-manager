package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.User;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.util.Collection;

public interface UserService {
    Collection<User> getAllUsers();
    Double getNetWorth() throws IOException, ParseException;
}
