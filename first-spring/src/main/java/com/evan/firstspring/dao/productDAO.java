package com.evan.firstspring.dao;

import com.evan.firstspring.pojo.product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface productDAO extends JpaRepository<product,Integer> {
}
