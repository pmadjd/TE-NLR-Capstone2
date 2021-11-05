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
    private JdbcUserDao jdbcUserDao;

    public TransferController(UserDao userDao, TransferDao transferDao, AccountDao accountDao, JdbcUserDao jdbcUserDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.jdbcUserDao = jdbcUserDao;
    }
    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public String getTransferHistory (Principal principal) {
        int userId  = userDao.findIdByUsername(principal.getName());
        return transferDao.transferHistory(userId).toString();
    }

    @RequestMapping(value = "/transfer/details", method = RequestMethod.GET)
    public Transfer getTransferDetails (Principal principal) {
        int transferId  = transfer.getTransferId();
        return transferDao.transferDetails(transferId);
    }

    @RequestMapping(value = "/transfer/send", method = RequestMethod.POST) //match post/put
    public String getTransferSend (Principal principal, @RequestBody Transfer transfer){  //, @requestbody transfer transfer
        //whereever transfer.getaccountFrom replace from with the id you get with the priciple
       int userIdFrom = userDao.findIdByUsername(principal.getName()); //account ids not user ids
       int userIdTo = userDao.getUserIdFromAccountTo(transfer.getAccountTo());
       int accountIdTo = transfer.getAccountTo();
        BigDecimal amount = transfer.getAmount();
        if(userIdFrom != userIdTo) {
            accountDao.subToBalance(userIdFrom, amount);
            accountDao.addToBalance(userIdTo, amount);
            return transferDao.transferSend(transfer.getAccountFrom(), transfer.getAccountTo(), amount, accountDao.viewBalance(userIdFrom).getBalance());
        }
        else{
            return "You Cannot Send Money To Yourself Baka";
        }
    }

    @RequestMapping(value = "/transfer/users", method = RequestMethod.GET)
    public String getUsers (Principal principal){
        String str = "";
        List<User> userList = jdbcUserDao.findAll();
        for(int i = 0; i < userList.size(); i++){
            str += ("Username: "+ userList.get(i).getUsername() + "  --  User Id: "+ userList.get(i).getId() + "\n");  //add line spacing
        }
        return str;
    }

}

