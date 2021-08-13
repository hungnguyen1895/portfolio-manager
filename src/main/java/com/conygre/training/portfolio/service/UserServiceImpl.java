package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.repo.InvestmentRepository;
import com.conygre.training.portfolio.repo.UserRepository;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private CashAccountService cashAccountService;

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Double getNetWorth() throws IOException, ParseException {
        return investmentService.getTotalInvestment() + cashAccountService.getCash();
    }
}
