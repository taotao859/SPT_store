package com.evan.firstspring.dao;

import com.evan.firstspring.pojo.staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface staffDAO extends JpaRepository<staff,Integer>{
    staff findByStaffId(String staff_id);

    staff findByStaffName(String staff_name);

    staff findByStaffIdAndStaffPassword(String staff_id, String staff_passord);

    Page<staff> findAll(Specification<staff> spec, Pageable pageable);
}
