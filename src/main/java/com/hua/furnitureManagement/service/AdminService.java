package com.hua.furnitureManagement.service;

import com.hua.furnitureManagement.pojo.dto.UserDTO;
import com.hua.furnitureManagement.pojo.vo.AdminVO;

public interface AdminService {
    // 管理员登录
    AdminVO login(UserDTO userDTO);
}
