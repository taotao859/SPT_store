package com.evan.firstspring.service;
import com.evan.firstspring.dao.UserDAO;
import com.evan.firstspring.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class userService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String username) {
        user U = getByName(username);
        return null!=U;
    }

    public user getByName(String username) {
        return userDAO.findByStaffId(username);
    }

    public user get(String username, String password){
        return userDAO.getByStaffIdAndStaffPassword(username, password);
    }

    public user getByNumber(int numebr, String password){
        return userDAO.getByNumberAndStaffPassword(numebr, password);
    }

    public void add(user user) {
        userDAO.save(user);
    }
}
