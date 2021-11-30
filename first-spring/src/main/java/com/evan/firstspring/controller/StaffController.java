package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Staff;
import com.evan.firstspring.mapper.StaffMapper;
import com.evan.firstspring.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin
public class StaffController {
    @Autowired
    private StaffMapper staffMapper;

    @PostMapping("add")
    public Result addStaff(@RequestBody Staff staff) {
        int i = staffMapper.insert(staff);

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @GetMapping("delete")
    public Result deleteStaff(@RequestBody Staff staff) {
        int i = staffMapper.deleteById(staff.getStaffId());

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @PostMapping("update")
    public Result updateStaff(@RequestBody Staff staff) {
        int i = staffMapper.updateById(staff);

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @GetMapping("search")
    public List<Staff> searchStaff(@RequestParam String staffName) {
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        staffQueryWrapper.eq("staff_name", staffName);

        return staffMapper.selectList(staffQueryWrapper);
    }

    @GetMapping("all")
    public List<Staff> getAllStaff() {
        return staffMapper.selectList(null);
    }
}
