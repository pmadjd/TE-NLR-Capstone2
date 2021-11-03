package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public  interface AccountDao {
    Account viewBalance(int userId);

    BigDecimal addToBalance(int userId, BigDecimal transferDeposit);

    BigDecimal subToBalance(int userId, BigDecimal transferWithdraw);
}

