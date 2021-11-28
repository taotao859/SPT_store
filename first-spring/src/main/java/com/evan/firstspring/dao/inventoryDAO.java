package com.evan.firstspring.dao;

import com.evan.firstspring.pojo.inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface inventoryDAO extends JpaRepository<inventory,Integer> {
}
