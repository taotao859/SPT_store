package com.evan.firstspring.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Staff;
import com.evan.firstspring.bean.VertifyCodeUtils;
import com.evan.firstspring.mapper.StaffMapper;
import com.evan.firstspring.result.Result;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {

    private String validate_code = null;

    @Autowired
    private StaffMapper staffMapper;


    //@GetMapping("hello")
    @RequestMapping("/login")
    public Result loginController(@RequestBody JSONObject data) {

        //System.out.println(data.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject =  data;
        System.out.println(jsonObject.getString("staffId"));
        String user_id = jsonObject.getString("staffId");
        String user_pswd = jsonObject.getString("staffPassword");
        String validate_code_input = jsonObject.getString("verifyCode");

        System.out.println("validate_code: " + validate_code);
        System.out.println("validate_code_input: " + validate_code_input);

        validate_code_input = validate_code_input.toLowerCase();
        validate_code = validate_code.toLowerCase();
        // 验证成功
        if(validate_code.equals(validate_code_input)){
            // 继续验证用户
            if(user_id.length()!=4){
                return new Result(405);
            }
            QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
            staffQueryWrapper.eq("staff_id", user_id);
            staffQueryWrapper.eq("staff_password", user_pswd);
            List<Staff> staffList = staffMapper.selectList(staffQueryWrapper);

            if (staffList.size() == 1) {
                //  staff_role区分不同角色，0——店长，1——仓库管理员，2——售货员
                int staff_role = staffList.get(0).getStaffRole();
                //  对饮状态码为200，201,202
                return new Result(200 + staff_role);
            } else {
                return new Result(400);
            }
        } else { // 验证失败
            return new Result(500);
        }
    }

    // 生成验证码
    @GetMapping("/getVerifyCodeImage")
    public String getImageCode(HttpServletRequest request) throws IOException{
        // 1. 使用工具类生成验证码
        String code = VertifyCodeUtils.generateVerifyCode(4);
        validate_code = code; // 存放生成的验证码
        System.out.println("validate_code: " + validate_code);
        // 2. 将验证码放入ServletContext作用域
        request.getServletContext().setAttribute("code", code);
        // 3. 将图片转换成base64格式
        // 字节数组输出流在内存中创建一个字节数组缓冲区，所有发送到输出流的数据保存在该字节数组缓冲区中
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //将得到的验证码，使用工具类生成验证码图片，并放入到字节数组缓存区
        VertifyCodeUtils.outputImage(160,30,byteArrayOutputStream,code);
        //使用spring提供的工具类，将字节缓存数组中的验证码图片流转换成Base64的形式
        //并返回给浏览器
        return "data:image/png;base64," + Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
    }
}
