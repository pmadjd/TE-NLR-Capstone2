package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
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
    private AccountDao accountDao;
    private Transfer transfer;
     //change to come from the body of the request

    public TransferController(UserDao userDao, TransferDao transferDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
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

    @RequestMapping(value = "/transfer/send", method = RequestMethod.POST) //match post/put
    public String getTransferSend (Principal principal, @RequestBody Transfer transfer){  //, @requestbody transfer transfer

       int userIdFrom = userDao.findIdByUsername(principal.getName()); //account ids not user ids
       int userIdTo = transfer.getAccountTo();
        BigDecimal amount = transfer.getAmount();
        accountDao.addToBalance(userIdTo, amount);
        accountDao.subToBalance(userIdFrom, amount);
        return transferDao.transferSend(transfer.getAccountFrom(), transfer.getAccountTo(), amount, accountDao.viewBalance(userIdFrom).getBalance());
    }

    @RequestMapping(value = "/transfer/users", method = RequestMethod.GET)
    public List<User> getUsers (Principal principal){
        return userDao.findAll();
    }

}
