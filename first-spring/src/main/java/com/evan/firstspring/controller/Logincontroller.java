package com.evan.firstspring.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Staff;
import com.evan.firstspring.mapper.StaffMapper;
import com.evan.firstspring.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {
    @Autowired
    private StaffMapper staffMapper;

    @PostMapping("login")
    public Result loginIn(@RequestBody Staff requestStaff) {
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        staffQueryWrapper.eq("staff_id", requestStaff.getStaffId());
        staffQueryWrapper.eq("staff_password", requestStaff.getStaffPassword());
        List<Staff> staffList = staffMapper.selectList(staffQueryWrapper);

        if (staffList.size() == 1) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }
}



