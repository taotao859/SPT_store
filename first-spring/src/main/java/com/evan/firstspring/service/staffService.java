package com.evan.firstspring.service;

import com.evan.firstspring.dao.staffDAO;
import com.evan.firstspring.pojo.staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class staffService {
    @Autowired
    staffDAO staffDAO;

    public boolean isExist(String staff_id) {
        staff staff = getByid(staff_id);
        return null!=staff;
    }

    public staff getByid(String staff_id) {
        return staffDAO.findByStaffId(staff_id);
    }

    public staff getByname(String staff_name) {
        return staffDAO.findByStaffName(staff_name);
    }

    public staff findByidAndPassword(String staff_id, String staff_password){
        return staffDAO.findByStaffIdAndStaffPassword(staff_id, staff_password);
    }

    public void saveStaff(staff staff) {
        staffDAO.save(staff);
    }

    public Page<staff> findAll(int page, int size, int staff_id) {
        Pageable pageable =PageRequest.of(page,size,new Sort(Sort.Direction.DESC,"id"));
        return staffDAO.findAll((Specification<staff>) (root, query, cb) -> {
            Predicate p1 = cb.like(root.get("staff_id").as(String.class),"%"+staff_id+"%");
            return cb.and(p1);
        },pageable);
    }

    public staff updateStaff(staff staff) {
        return staffDAO.save(staff);
    }

    public List<staff> findAllinList() {
        return staffDAO.findAll();
    }

}
