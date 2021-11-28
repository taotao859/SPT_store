package com.evan.firstspring.dao;

import com.evan.firstspring.pojo.customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface customerDAO extends JpaRepository<customer,Integer> {
}
