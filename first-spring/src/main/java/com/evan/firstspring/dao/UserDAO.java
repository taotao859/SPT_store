package com.evan.firstspring.dao;

import com.evan.firstspring.pojo.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<user,Integer> {
    user findByStaffId(String username);

    user getByStaffIdAndStaffPassword(String username,String password);

    user getByNumberAndStaffPassword(int number, String password);
}

