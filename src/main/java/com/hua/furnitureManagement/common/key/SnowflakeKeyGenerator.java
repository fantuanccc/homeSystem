package com.hua.furnitureManagement.common.key;

import com.hua.furnitureManagement.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.Random;

/**
 * 生成密钥 （雪花算法）
 * @Author hua
 * @Date 2025/4/29
 */
@Slf4j
public class SnowflakeKeyGenerator {

    // Snowflake参数（可以根据需要调整）
    private static final long EPOCH = 1288834974657L; // 起始时间戳
    private static final long MACHINE_ID_BITS = 5L;   // 机器ID位数
    private static final long DATA_CENTER_ID_BITS = 5L; // 数据中心ID位数
    private static final long SEQUENCE_BITS = 12L;    // 序列号位数

    private static final long MAX_MACHINE_ID = ~(-1L << MACHINE_ID_BITS); // 最大机器ID
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS); // 最大数据中心ID
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS); // 最大序列号

    private long machineId; // 机器ID
    private long dataCenterId; // 数据中心ID
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上次生成ID的时间戳

    private Random random = new Random();

    public SnowflakeKeyGenerator(long machineId, long dataCenterId) {
        if (machineId > MAX_MACHINE_ID || machineId < 0) {
            throw new IllegalArgumentException("Machine ID is out of range");
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException("Data Center ID is out of range");
        }
        this.machineId = machineId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized String generateKey() {
        long timestamp = System.currentTimeMillis() - EPOCH;
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = waitForNextMillis(lastTimestamp);
            }
        } else {
            sequence = random.nextInt((int) SEQUENCE_MASK + 1); // 随机生成序列号
        }

        lastTimestamp = timestamp;

        // 生成64位ID（Snowflake ID结构：1bit符号位 + 41bit时间戳 + 5bit数据中心ID + 5bit机器ID + 12bit序列号）
        long id = (timestamp << (MACHINE_ID_BITS + DATA_CENTER_ID_BITS + SEQUENCE_BITS))
                | (dataCenterId << (MACHINE_ID_BITS + SEQUENCE_BITS))
                | (machineId << SEQUENCE_BITS)
                | sequence;

        // 将生成的long类型ID转换为字符串
        return Long.toHexString(id); // 转换为十六进制字符串
    }

    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis() - EPOCH;
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis() - EPOCH;
        }
        return timestamp;
    }

    // 获取本机的IP地址，并通过IP地址生成机器ID
    public static long getMachineId() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            String hostAddress = ip.getHostAddress();
            // 你可以通过将IP地址转换为数字来得到机器ID
            long machineId = 0;
            String[] parts = hostAddress.split("\\.");
            for (int i = 0; i < parts.length; i++) {
                machineId |= (Long.parseLong(parts[i]) << (8 * (3 - i)));
            }
            return machineId & 0x1F;  // 限制机器ID在5位以内
        } catch (Exception e) {
            log.error("获取机器ID失败, 原因：{}", e.getMessage(), e);
            throw new GlobalException("获取机器ID失败，原因：" + e.getMessage());
        }
    }
}
