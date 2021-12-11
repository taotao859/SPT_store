package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Pandian {
    @TableId(type = IdType.AUTO)
    private int pandianId;
    private int pandianProductId;
    private int pandianRepositoryId;
    private int pandianChangeQuantity;

    public int getPandianId() {
        return pandianId;
    }

    public void setPandianId(int pandianId) {
        this.pandianId = pandianId;
    }

    public int getPandianProductId() {
        return pandianProductId;
    }

    public void setPandianProductId(int pandianProductId) {
        this.pandianProductId = pandianProductId;
    }

    public int getPandianRepositoryId() {
        return pandianRepositoryId;
    }

    public void setPandianRepositoryId(int pandianRepositoryId) {
        this.pandianRepositoryId = pandianRepositoryId;
    }

    public int getPandianChangeQuantity() {
        return pandianChangeQuantity;
    }

    public void setPandianChangeQuantity(int pandianChangeQuantity) {
        this.pandianChangeQuantity = pandianChangeQuantity;
    }
}
