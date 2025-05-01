package com.hua.furnitureManagement.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hua.aliyunsms")
@Data
public class SmsProperties {
    private String accessKeyId;
    private String accessKeySecret;
}
