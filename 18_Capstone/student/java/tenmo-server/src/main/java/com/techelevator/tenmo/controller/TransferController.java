package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
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
    private JdbcTransferDao jdbcTransferDao;

    public TransferController(UserDao userDao, TransferDao transferDao, AccountDao accountDao, JdbcUserDao jdbcUserDao, JdbcTransferDao jdbcTransferDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.jdbcUserDao = jdbcUserDao;
        this.jdbcTransferDao = jdbcTransferDao;

    }
    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public String getTransferHistory (Principal principal) {
        int userId  = userDao.findIdByUsername(principal.getName());
        String strAccountId = String.valueOf(userDao.getAccountIdFromUserId(userId));
        return strAccountId;
    }

    @RequestMapping(value = "/transfer/details", method = RequestMethod.GET)
    public String getTransferDetails (Principal principal) {  //send as a list and then loop through in the front end, response entity will be a transfer ARRAY and then use the getters to print the information out
        int userId = userDao.findIdByUsername(principal.getName());
        String str = "";
        List<Transfer> list = jdbcTransferDao.transferHistory(userId);
        for(int i = 0; i < list.size(); i++){
            str +=  "\n Transfer Id: " + list.get(i).getTransferId() +
                    "\n Transfer Type Id: " +list.get(i).getTransferTypeId() +
                    "\n Transfer Status Id: " + list.get(i).getTransferStatusId() +
                    "\n From Account: " + list.get(i).getAccountFrom() +
                    "\n To Account: " + list.get(i).getAccountTo() +
                    "\n Amount: " + list.get(i).getAmount().toString() + "\n";
        }
        return str;
    }

    @RequestMapping(value = "/transfer/send", method = RequestMethod.POST) //match post/put
    public String getTransferSend (@RequestBody TransferDTO transferDTO){  //, @requestbody transfer transfer
        //whereever transfer.getaccountFrom replace from with the id you get with the priciple
       int userIdFrom = transferDTO.getUserIdFrom();
       int accountIdFrom = userDao.getAccountIdFromUserId(userIdFrom);
       int userIdTo = transferDTO.getUserIdTo();  //userDao.getUserIdFromAccountTo(transferDTO.getUserId());
       int accountIdTo = userDao.getAccountIdFromUserId(userIdTo);
        BigDecimal amount = transferDTO.getAmount();  //transferDTO.getAmount();
        if(userIdFrom != userIdTo) {
            accountDao.subToBalance(userIdFrom, amount);
            accountDao.addToBalance(userIdTo, amount);
            return transferDao.transferSend(accountIdFrom, accountIdTo, amount, accountDao.viewBalance(userIdFrom).getBalance());
            //make method that turns userID into account id then pass it into here
            //
        }
        else{
            return "You Can Not Send Money To Yourself!";
        }
    }

    @RequestMapping(value = "/transfer/users", method = RequestMethod.GET)
    public String getUsers (Principal principal){
        String str = "";
        List<User> userList = jdbcUserDao.findAll();
        for(int i = 0; i < userList.size(); i++){
            str += ("Username: "+ userList.get(i).getUsername() + "  --  User Id: "+ userList.get(i).getId() + "\n");
        }
        return str;
    }

}

