package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTransferDao() {

    }

    public Transfer transferSend(int userId) {
        /* String sql = "SELECT balance " +
                "FROM accounts " +
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            BigDecimal balance = mapRowToAccount(results).getBalance();
            balance.add(transferDeposit);
            String sqlUpdate = "UPDATE accounts " +
                    "SET balance = ? " +
                    "WHERE user_id = ?;";
            jdbcTemplate.update(sqlUpdate, balance, userId);
            //add system print for the amount before and after, also need to log this for the user

        }
        */
        return null;
    }


    public Transfer transferLog(int userId) {  //send and received
        return null;
    }

    public List<Transfer> transferDetails(int userId) {//store in sql file after every transaction linked with their TransferID then when they want the details have this print put all the SQL statements with their transferID
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM transfers" +
                "FULL OUTER JOIN account "+
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
        while (results.next()){
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferTypeId(results.getInt("transfer_type_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_id"));
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }
}

