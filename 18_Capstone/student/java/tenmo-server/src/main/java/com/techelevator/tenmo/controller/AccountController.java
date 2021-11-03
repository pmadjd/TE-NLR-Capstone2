package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.LoginDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance (int userId) {
        JdbcAccountDao jdbcAccountDao = new JdbcAccountDao();
        return jdbcAccountDao.viewBalance(userId).getBalance();
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public List<Transfer> getTransfers (int userId) {
        JdbcTransferDao jdbcTransferDao = new JdbcTransferDao();

        return jdbcTransferDao.transferDetails(userId);
    }
}
