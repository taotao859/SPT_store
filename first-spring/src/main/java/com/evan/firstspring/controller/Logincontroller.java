package com.evan.firstspring.controller;

import com.evan.firstspring.pojo.staff;
import com.evan.firstspring.result.Result;
import com.evan.firstspring.service.staffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@Controller
public class Logincontroller {

    @Autowired
    staffService staffService;

    @CrossOrigin
    @RequestMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody staff requeststaff) {
        String username = requeststaff.getStaffId();
        staff staff = staffService.findByidAndPassword(requeststaff.getStaffId(), requeststaff.getStaffPassword());
        if (null == staff) {
            return new Result(400);
        } else {
            return new Result(200);
        }
    }
}
//package com.evan.firstspring.controller;
//
//import com.evan.firstspring.pojo.user;
//import com.evan.firstspring.result.Result;
//import com.evan.firstspring.service.userService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.HtmlUtils;
//
//@Controller
//class LoginController {
//
//    @Autowired
//    userService userService;
//
//    @CrossOrigin
//    @PostMapping(value = "/api/login")
//    @ResponseBody
//    public Result login(@RequestBody user requestUser) {
//        String username = requestUser.getStaffId();
//        username = HtmlUtils.htmlEscape(username);
//
//        user user = userService.get(username, requestUser.getStaffPassword());
//        if (null == user) {
//            return new Result(400);
//        } else {
//            return new Result(200);
//        }
//    }
//}


