package com.oves.baseframework.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * IP获取工具, 除在/etc/hosts中显式配置外, 保证不获取到环回地址和ipv6地址等带冒号的地址
 */
public class IpUtil {

    public static String getRealIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP

        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
}
