package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDTO {
    private int userId;
    private BigDecimal amount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransferDTO(int userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }
    //configure backend to receive this DTO
}
