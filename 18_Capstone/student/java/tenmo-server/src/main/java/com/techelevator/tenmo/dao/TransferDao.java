package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    Transfer transferSend (int userId);
    Transfer transferLog (int userId);
    List<Transfer> transferDetails (int userId);
}