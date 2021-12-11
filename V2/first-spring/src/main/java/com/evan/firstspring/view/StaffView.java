package com.evan.firstspring.view;

import com.evan.firstspring.bean.Staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaffView {
    private int index;
    private String id;
    private String name;
    private String title;

    public static List<StaffView> getStaffViewList(List<Staff> staffList)
    {
        List<String> titleList= Arrays.asList("老板","仓库管理员","销售员");
        List<StaffView> staffViewList=new ArrayList<>();

        for(int i=0;i<staffList.size();i++)
        {
            Staff staff=staffList.get(i);
            staffViewList.add(new StaffView(i,staff.getStaffId(),staff.getStaffName(),titleList.get(staff.getStaffRole())));
        }

        return staffViewList;
    }


    public StaffView(int index, String id, String name, String title) {
        this.index = index;
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
