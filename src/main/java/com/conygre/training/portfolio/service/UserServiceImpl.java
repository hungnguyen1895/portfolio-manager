package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.repo.InvestmentRepository;
import com.conygre.training.portfolio.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }
}
