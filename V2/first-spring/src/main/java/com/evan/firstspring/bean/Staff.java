package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;

public class Staff {
    @TableId(type = IdType.AUTO)
    private String staffId;
    private String staffPassword;
    private String staffName;
    private int staffRole;
    private String staffContact;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public int getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(int staffRole) {
        this.staffRole = staffRole;
    }

    public String getStaffContact() {
        return staffContact;
    }

    public void setStaffContact(String staffContact) {
        this.staffContact = staffContact;
    }
}
