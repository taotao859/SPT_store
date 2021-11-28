package com.evan.firstspring.dao;

import com.evan.firstspring.pojo.orderitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderitemDAO extends JpaRepository<orderitem,Integer> {
}
