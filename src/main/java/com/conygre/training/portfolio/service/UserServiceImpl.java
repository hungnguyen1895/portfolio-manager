package com.conygre.training.portfolio.service;

import com.conygre.training.portfolio.DAO.MarketDAO;
import com.conygre.training.portfolio.entities.Investment;
import com.conygre.training.portfolio.entities.User;
import com.conygre.training.portfolio.pojo.StockWithPercent;
import com.conygre.training.portfolio.repo.InvestmentRepository;
import com.conygre.training.portfolio.repo.UserRepository;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.conygre.training.portfolio.service.MarketServiceImpl.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private CashAccountService cashAccountService;

    @Autowired
    private MarketDAO marketDAO;

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Double getNetWorth(Integer userID) throws IOException, ParseException {
        return investmentService.getTotalInvestment(userID) + cashAccountService.getCashByUser(userID);
    }

    @Override
    public List<StockWithPercent> getUserGainersAndLosers(Integer userID) {
        List<String> symbols = new LinkedList<>();
        List<StockWithPercent> valuesList;
        Collection<Investment> investments = investmentRepository.findByUserId(userID);
        //get stock symbols for users
        for(Investment investment : investments){
            symbols.add(investment.getStockSymbol());
        }

        //get change percents for each stock
        try{
            valuesList = marketDAO.getMarketChangePercents(symbols);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }


        if(valuesList != null){
            sortListBasedOnMarketChangePercent(valuesList);
            Collections.reverse(valuesList);
        }


        return valuesList;
    }
}
