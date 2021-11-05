package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
public class TransferController {
    private UserDao userDao;
    private TransferDao transferDao;
    private Transfer transfer;
    private JdbcUserDao jdbcUserDao;

    public TransferController(UserDao userDao, TransferDao transferDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
    }
    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory (Principal principal) {
        int userId  = userDao.findIdByUsername(principal.getName());
        return transferDao.transferHistory(userId);
    }

    @RequestMapping(value = "/transfer/details", method = RequestMethod.GET)
    public Transfer getTransferDetails (Principal principal) {
        int transferId  = transfer.getTransferId();
        return transferDao.transferDetails(transferId);
    }

    @RequestMapping(value = "/transfer/send", method = RequestMethod.POST)
    public String getTransferSend (Principal principal){
        jdbcUserDao.allUserId();
        int userIdFrom = userDao.findIdByUsername(principal.getName());
        int userIdTo = transfer.getAccountTo();
        BigDecimal amount = transfer.getAmount();
        return transferDao.transferSend(userIdFrom, userIdTo, amount);
    }

}
