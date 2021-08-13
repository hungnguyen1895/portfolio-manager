package com.conygre.training.portfolio.rest;

import com.conygre.training.portfolio.service.CashAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/cashaccount")
public class CashAccountController {
    @Autowired
    private CashAccountService cashAccountService;

}
