package com.hua.furnitureManagement.service;

import java.util.Map;

/**
 * 发送短信验证码服务接口
 * @Author hua
 * @Date 2025/4/30
 */
public interface SendSms {

    /**
     * 发送短信验证码
     * @param phoneNumber
     * @param map
     * @return
     */
    Boolean send(String phoneNumber, Map<String, Object> map);
}

