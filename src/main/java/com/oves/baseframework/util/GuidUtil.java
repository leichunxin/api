package com.oves.baseframework.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 全局32位唯一标识符生成器, 前提: 单台机器10w/ms以内
 * 注意: 依赖不同机器的不同IP, 务必保证每台机器获取到的IP不是环回地址或类似地址, 而是外网地址
 */
public class GuidUtil {

    // 自增计数器，最大五位（99999），理论上在单台机器10w/ms以内的前提下保证全局唯一
    private static AtomicInteger inc = new AtomicInteger(0);

    // 16进制IP，最大8位
    private static String        ipHex;

    static {
        String ip = "";
        try {
            ip = IpUtil.getRealIp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ipHex = "";
        String[] split = ip.split("\\.");
        for (String string : split) {
            ipHex += Integer.toHexString(Integer.valueOf(string));
        }
    }

    /**
     * 总数=自动生成不重复最长26位+seed位数（后缀, 最长6位）
     *
     * @param seed
     * @return
     */
    public static String getNextUid(String seed) {
        int curr = inc.getAndIncrement();
        if (curr >= 99999) {
            inc.getAndSet(curr % 99999);
        }
        String postfix = (seed == null) ? "" : seed;
        String currentTmls = String.valueOf(System.currentTimeMillis());
        return curr + currentTmls + ipHex + postfix;
    }
}
