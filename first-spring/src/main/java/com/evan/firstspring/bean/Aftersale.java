package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;

public class Aftersale {
    @TableId(type = IdType.AUTO)
    private int afterSaleId;

    private int afterSaleOrdersId;

    public int getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(int afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public int getAfterSaleOrdersId() {
        return afterSaleOrdersId;
    }

    public void setAfterSaleOrdersId(int afterSaleOrdersId) {
        this.afterSaleOrdersId = afterSaleOrdersId;
    }
}
