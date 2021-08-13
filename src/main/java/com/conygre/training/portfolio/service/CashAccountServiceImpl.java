package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.CashAccount;
import com.conygre.training.portfolio.repo.CashAccountRepository;
import com.conygre.training.portfolio.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CashAccountServiceImpl implements CashAccountService {
    @Autowired
    private CashAccountRepository cashAccountRepository;


    @Override
    public Collection<CashAccount> getAllAccounts() {
        return cashAccountRepository.findAll();
    }

    @Override
    public Double getCash() {
        Collection<CashAccount> cashAccounts = getAllAccounts();
        Double totalCash = 0.0;
        for (CashAccount acc: cashAccounts) {
            totalCash += acc.getValue();
        }


        return totalCash;
    }


}
