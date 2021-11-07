package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDTO {
    private int userIdTo;
    private BigDecimal amount;
    private int userIdFrom;

    public void setUserIdTo(int userIdTo) {
        this.userIdTo = userIdTo;
    }

    public int getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(int userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public int getUserIdTo() {
        return userIdTo;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransferDTO(int userIdTo, BigDecimal amount, int userIdFrom) {
        this.userIdTo = userIdTo;
        this.amount = amount;
        this.userIdFrom = userIdFrom;
    }
    //configure backend to receive this DTO
}
