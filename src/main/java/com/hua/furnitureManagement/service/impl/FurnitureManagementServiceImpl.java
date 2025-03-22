package com.hua.furnitureManagement.service.impl;

import com.hua.furnitureManagement.service.FurnitureManagementService;
import org.springframework.stereotype.Service;

/**
 * 家具管理服务实现类
 *
 * @Author 曲冠华
 * @Date 2025/2/17
 */
@Service("furnitureManagementService")
public class FurnitureManagementServiceImpl implements FurnitureManagementService {

    @Override
    public void test() {
        System.out.println("接口调通测试");
    }
}
