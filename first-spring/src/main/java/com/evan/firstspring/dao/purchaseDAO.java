package com.evan.firstspring.dao;

import com.evan.firstspring.pojo.purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface purchaseDAO extends JpaRepository<purchase,Integer> {
}
