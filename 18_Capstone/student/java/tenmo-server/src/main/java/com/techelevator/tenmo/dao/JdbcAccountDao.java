package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account viewBalance(int userId) {
        String sql = "SELECT balance " +
                "FROM accounts "+
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
        if (results.next()){
            return mapRowToAccount(results);
        }
        return null; //placeholder for now
    }

    @Override
    public BigDecimal addToBalance(int userId, BigDecimal transferDeposit) {
        String sql = "SELECT balance " +
                "FROM accounts "+
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()){
            BigDecimal balance =  mapRowToAccount(results).getBalance();
            balance.add(transferDeposit);
            String sqlUpdate = "UPDATE accounts "+
                    "SET balance = ? " +
                    "WHERE user_id = ?;";
            jdbcTemplate.update(sqlUpdate, balance, userId);
            //add system print for the amount before and after, also need to log this for the user
            return balance;
        }
        return null;
    }

    @Override
    public BigDecimal subToBalance(int userId,BigDecimal transferWithdraw) {
        String sql = "SELECT balance " +
                "FROM accounts "+
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()){
            BigDecimal balance =  mapRowToAccount(results).getBalance();
            if (balance.compareTo(transferWithdraw) > 0) {     //make sure that this is subtracting first and seeing if it is larger than 0 not really sure
                balance.subtract(transferWithdraw);
                String sqlUpdate = "UPDATE accounts " +
                        "SET balance = ? " +
                        "WHERE user_id = ?;";
                jdbcTemplate.update(sqlUpdate, balance, userId);
                //add system print for the amount before and after, also need to log this for the user
                return balance;
            }
            else {
                System.out.println("Balance Too Low To Transfer This Amount");
                return null;
            }
        }
        return null;
    }

    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();
        account.setUserId(results.getInt("user_id"));
        account.setAccountId(results.getInt("account_id"));
        account.setBalance(results.getBigDecimal("balance"));
        return account;
    }
}
