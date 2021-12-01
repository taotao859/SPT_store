package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;
import org.joda.time.DateTime;

public class Record {
    @TableId(type = IdType.AUTO)
    private int recordId;
    private int recordRepositoryId;
    private int recordProductId;
    private int recordQuantity;
    private DateTime recordTime;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getRecordRepositoryId() {
        return recordRepositoryId;
    }

    public void setRecordRepositoryId(int recordRepositoryId) {
        this.recordRepositoryId = recordRepositoryId;
    }

    public int getRecordProductId() {
        return recordProductId;
    }

    public void setRecordProductId(int recordProductId) {
        this.recordProductId = recordProductId;
    }

    public int getRecordQuantity() {
        return recordQuantity;
    }

    public void setRecordQuantity(int recordQuantity) {
        this.recordQuantity = recordQuantity;
    }

    public DateTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(DateTime recordTime) {
        this.recordTime = recordTime;
    }
}
