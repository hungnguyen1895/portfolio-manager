package com.conygre.training.portfolio.service;

import org.springframework.data.crossstore.ChangeSetPersister;

public interface CashAccountService {
    Double getCashByUser(Integer id);
}
