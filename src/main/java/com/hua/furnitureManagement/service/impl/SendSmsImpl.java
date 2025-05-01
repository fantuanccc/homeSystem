package com.hua.furnitureManagement.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.hua.furnitureManagement.common.properties.SmsProperties;
import com.hua.furnitureManagement.service.SendSms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 发送短信服务实现类
 * @Author hua
 * @Date 2025/4/30
 */
@Service
@Slf4j
public class SendSmsImpl implements SendSms {
    @Autowired
    private SmsProperties smsProperties;

    @Override
    public Boolean send(String phoneNumber, Map<String, Object> map) {
        //连接阿里云,后两参数输入自己阿里云账号中生成的AccessKey ID和SECRET的值
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        //固定内容
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //自定义参数（手机号、验证码、签名、模板）
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "阿里云短信测试");
        request.putQueryParameter("TemplateCode", "SMS_154950909");
        //构建短信验证码
        request.putQueryParameter("TemplateParam", JSONUtil.toJsonStr(map));
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("阿里云api返回信息：{}", response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}

