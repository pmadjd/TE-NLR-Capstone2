package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    String transferSend (int userFrom, int userTo, BigDecimal amount);

    Transfer transferDetails (int userId);  //these methods should be returning strings not Accounts

    List<Transfer> transferHistory (int userId);

}