package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Staff;
import com.evan.firstspring.mapper.StaffMapper;
import com.evan.firstspring.result.Result;
import com.evan.firstspring.view.StaffView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin
public class StaffController {
    @Autowired
    private StaffMapper staffMapper;

    @GetMapping("add")
    public Result addStaff(@RequestParam String staffId,@RequestParam String staffName,@RequestParam String staffTitle) {
        Staff staff=new Staff();
        staff.setStaffName(staffName);
        if(staffTitle.equals("销售员"))
        {
            staff.setStaffRole(2);
        }else if(staffTitle.equals("仓库管理员"))
        {
            staff.setStaffRole(1);
        }
        staff.setStaffId(staffId);
        staff.setStaffPassword("1234");
        int i = staffMapper.insert(staff);

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @PostMapping("delete")
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
    public List<StaffView> getAllStaff() {
        return StaffView.getStaffViewList(staffMapper.selectList(null));
    }

    @GetMapping("get")
    public Staff getSpecificStaff(@RequestParam String staffId){
        return staffMapper.selectById(staffId);
    }
}
