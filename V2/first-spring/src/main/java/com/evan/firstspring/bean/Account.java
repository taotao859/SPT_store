package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

public class Account {
    @TableId(type = IdType.AUTO)
    private int accountId;
    private BigDecimal accountAmount;
    private Timestamp accountTime;
    private int accountIndex;
    private boolean accountTag;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }

    public Timestamp getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(Timestamp accountTime) {
        this.accountTime = accountTime;
    }

    public int getAccountIndex() {
        return accountIndex;
    }

    public void setAccountIndex(int accountIndex) {
        this.accountIndex = accountIndex;
    }

    public boolean isAccountTag() {
        return accountTag;
    }

    public void setAccountTag(boolean accountTag) {
        this.accountTag = accountTag;
    }
}
