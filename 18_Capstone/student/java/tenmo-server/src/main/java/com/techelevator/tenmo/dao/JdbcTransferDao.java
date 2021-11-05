package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Transfer transfer = new Transfer();

    public AccountDao accountDao;

    public JdbcAccountDao jdbcAccountDao;

    public Account account = new Account();

    public String transferSend(int userIdFrom, int userIdTo, BigDecimal amount, BigDecimal startingBalance) {
        if(startingBalance.compareTo(amount) >= 0){
            String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "+
                    "VALUES (?,?,?,?,?) ;";
            //make 2 constants and name them public static final
            jdbcTemplate.update(sql, 2, 2,userIdFrom, userIdTo, amount);
            return "Transfer complete"; //
        }else{
            return "ERROR Not Enough Money, Can Not Transfer More Than You Have";
        }
    }

    /*
        public String transferSend(int userIdFrom, int userIdTo, BigDecimal amount) {
        if(jdbcAccountDao.viewBalance(userIdFrom).getBalance().compareTo(amount) >= 0){
            String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "+
                    "VALUES (?,?,?,?,?) ;";
            //make 2 constants and name them public static final
            jdbcTemplate.update(sql, 2, 2,userIdFrom, userIdTo, amount);
            accountDao.addToBalance(userIdTo, amount);
            accountDao.subToBalance(userIdFrom, amount);
            return "Transfer complete";
        }else{
            return "ERROR Not Enough Money, Can Not Transfer More Than You Have";
        }
    }
     */


    public List<Transfer> transferHistory(int userId) {//store in sql file after every transaction linked with their TransferID then when they want the details have this print put all the SQL statements with their transferID
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM transfers " +
                "FULL OUTER JOIN account " +
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
        while (results.next()){
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    public Transfer transferDetails(int transferId) {
        String sql = "SELECT * " +
                "FROM transfers " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,transferId);
        if(results.next()){
            transfer = mapRowToTransfer(results);
        }else{
            System.out.println("ERROR During Jdbc Transfer Dao Transfer Details Method");
        }
        return transfer;
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


/*
         String sql = "SELECT balance " +
                "FROM accounts " +
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userFrom);
        BigDecimal balance = jdbcAccountDao.viewBalance(userFrom).getBalance();
        if (results.next()) {
            if(balance.compareTo(amount) >= 0) {
                balance.subtract(amount);
                String sqlUpdate = "UPDATE accounts " +
                        "SET balance = ? " +
                        "WHERE user_id = ?;";
                jdbcTemplate.update(sqlUpdate, balance, userFrom);
                String sqlTo = "SELECT balance "+
                        "FROM accounts "+
                        "WHERE user_id = ?;";
                SqlRowSet resultsTo = jdbcTemplate.queryForRowSet(sqlTo, userTo);
                BigDecimal balanceTo = jdbcAccountDao.viewBalance(userTo).getBalance();
                balanceTo.add(amount);
                String sqlUpdateTo = "UPDATE accounts "+
                        "SET balance = ? "+
                        "WHERE user_id = ?;";
                jdbcTemplate.update(sqlUpdateTo, balanceTo, userTo);


 */