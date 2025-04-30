package com.hua.furnitureManagement.service.impl;

import com.hua.furnitureManagement.dao.AdminDAO;
import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.pojo.entry.AdminDO;
import com.hua.furnitureManagement.pojo.vo.AdminVO;
import com.hua.furnitureManagement.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDAO adminDAO;
    @Override
    public AdminVO login(UserDTO userDTO) {
        AdminVO userVO = new AdminVO();
        AdminDO adminDO = new AdminDO();
        adminDO.setPhoneNumber(userDTO.getPhoneNumber());
        AdminDO admin = adminDAO.login(adminDO);
        if (admin == null) {
            throw new RuntimeException("手机号或密码错误");
        }
        if (!admin.getPassword().equals(userDTO.getPassword())) {
            throw new RuntimeException("手机号或密码错误");
        }
        BeanUtils.copyProperties(admin, userVO);
        return userVO;
    }
}
