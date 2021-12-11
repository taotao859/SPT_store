package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;
import org.joda.time.DateTime;

import java.sql.Timestamp;

public class Record {
    @TableId(type = IdType.AUTO)
    private int recordId;
    private int recordRepositoryId;
    private int recordProductId;
    private int recordQuantity;
    private Timestamp recordTime;

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

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }
}
