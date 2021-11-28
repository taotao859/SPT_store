package com.evan.firstspring.dao;

import com.evan.firstspring.pojo.orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ordersDAO extends JpaRepository<orders,Integer> {
}
