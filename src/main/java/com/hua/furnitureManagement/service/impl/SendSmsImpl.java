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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 发送短信服务实现类
 * @Author hua
 * @Date 2025/4/30
 */
@Service
public class SendSmsImpl implements SendSms {
    @Autowired
    private SmsProperties smsProperties;

    @Override
    public Boolean send(String phoneNumber, String templateCode, Map<String, Object> map) {
        //连接阿里云,后两参数输入自己阿里云账号中生成的AccessKey ID和SECRET的值
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        //不要改动
        request.setSysDomain("dysmsapi.aliyuncs.com");
        //不要改动
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        //自定义参数（手机号、验证码、签名、模板），左边参数为固定写法不可更改
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        //后面参数为阿里云账号中签名模板的签名名称
        request.putQueryParameter("SignName", smsProperties.getSignName());
        request.putQueryParameter("TemplateCode", templateCode);
        //构建短信验证码
        request.putQueryParameter("TemplateParam", JSONUtil.toJsonStr(map));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}

