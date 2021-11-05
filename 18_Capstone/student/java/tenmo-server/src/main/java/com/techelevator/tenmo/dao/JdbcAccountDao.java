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
        String sql = "SELECT * " +
                "FROM accounts "+
                "WHERE user_id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
        if (results.next()){
            return mapRowToAccount(results);
        }
        return null; //placeholder for now
    }

    @Override
    public BigDecimal addToBalance(int userIdTo, BigDecimal amount) {
        String sql = "SELECT * " +
                "FROM accounts "+
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userIdTo);
        if (results.next()){
            BigDecimal balance =  mapRowToAccount(results).getBalance();
            balance = balance.add(amount);
            String sqlUpdate = "UPDATE accounts "+
                    "SET balance = ? " +
                    "WHERE user_id = ?;";
            jdbcTemplate.update(sqlUpdate, balance, userIdTo);
            return balance;
        }
        else {
            System.out.println("Error Occurred While Adding to User");
        }
        return null;
    }

    @Override
    public BigDecimal subToBalance(int userIdFrom,BigDecimal amount) {
        String sql = "SELECT * " +
                "FROM accounts "+
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userIdFrom);
        if (results.next()){
            BigDecimal balance =  mapRowToAccount(results).getBalance();
            if (balance.compareTo(amount) >= 0) {
                balance = balance.subtract(amount);
                String sqlUpdate = "UPDATE accounts " +
                        "SET balance = ? " +
                        "WHERE user_id = ?;";
                jdbcTemplate.update(sqlUpdate, balance, userIdFrom);
                return balance;
            }
            else {
                System.out.println("Balance Too Low To Transfer This Amount");
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
