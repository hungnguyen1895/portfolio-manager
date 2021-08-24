package com.conygre.training.portfolio.rest;

import com.conygre.training.portfolio.service.CashAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/cashaccount")
public class CashAccountController {
    @Autowired
    private CashAccountService cashAccountService;

    @GetMapping("/{id}")
    public Double getTotalCash(@PathVariable("id") Integer id) throws ChangeSetPersister.NotFoundException {
        return cashAccountService.getCashByUser(id);
    }
}