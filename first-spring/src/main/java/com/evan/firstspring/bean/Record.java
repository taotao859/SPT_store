package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;
import org.joda.time.DateTime;

public class Record {
    @TableId(type = IdType.AUTO)
    private int recordId;
    private String recordRepository;
    private int recordProductId;
    private int recordQuantity;
    private DateTime recordTime;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getRecordRepository() {
        return recordRepository;
    }

    public void setRecordRepository(String recordRepository) {
        this.recordRepository = recordRepository;
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
