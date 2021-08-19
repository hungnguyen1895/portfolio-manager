package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.entities.CashAccount;
import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.repo.CashAccountRepository;
import com.conygre.training.portfolio.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CashAccountServiceImpl implements CashAccountService {
    @Autowired
    private CashAccountRepository cashAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Double getCashByUser(Integer id) {

        User user = userRepository.findById(id).get();
        Collection<CashAccount> cashAccounts = user.getCashAccounts();
        Double totalCash = 0.0;
        for (CashAccount acc: cashAccounts) {
            totalCash += acc.getValue();
        }

        return totalCash;
    }


}
