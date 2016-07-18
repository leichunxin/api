package com.oves.baseframework.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 *
 * @author jin.qian
 * @version $Id: LogUtils.java, v 0.1 2015年10月23日 下午9:01:15 jin.qian Exp $
 */
public class LogUtils {

    private static final Logger perfLogger = LoggerFactory.getLogger(LogConstants.COMMON_PERF);

    /**
     * 记录性能日志
     *
     * @param dumpValue
     */
    public static void dumpPerf(String dumpValue) {
        if (perfLogger.isInfoEnabled()) {
            perfLogger.info(dumpValue);
        }
    }

    /**
     * 记录debug日志
     */
    public static void debug(Logger logger, String msg, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format(msg, params));
        }
    }

    /**
     * 记录debug日志
     */
    public static void debug(Logger logger, String msg) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
        }
    }

    /**
     * 记录info日志
     */
    public static void info(Logger logger, String msg, Object... params) {
        if (logger.isInfoEnabled()) {
            logger.info(String.format(msg, params));
        }
    }

    /**
     * 记录info日志
     */
    public static void info(Logger logger, String msg) {
        if (logger.isInfoEnabled()) {
            logger.info(msg);
        }
    }

    /**
     * 记录warn日志
     */
    public static void warn(Logger logger, String msg, Object... params) {
        if (logger.isWarnEnabled()) {
            logger.warn(String.format(msg, params));
        }
    }

    /**
     * 记录warn日志
     */
    public static void warn(Logger logger, Throwable e, String msg, Object... params) {
        if (logger.isWarnEnabled()) {
            logger.warn(String.format(msg, params), e);
        }
    }

    /**
     * 记录error日志
     */
    public static void error(Logger logger, Throwable e, String msg, Object... params) {
        logger.error(String.format(msg, params), e);
    }

    /**
     * 记录error日志
     */
    public static void error(Logger logger, String msg, Object... params) {
        logger.error(String.format(msg, params));
    }

    /**
     * 记录日志, 带监控埋点
     * @param metric falcon的监控指标，这里规范为日志记录的“服务类型”，开发可自行定义
     * @param errorcode falcon监控指标metric的细分tag，这里是服务的errorcode状态码，开发可自行定义,如:E_DB_XX、SUCCESS
     * @param logger  Logger实例
     * @param msg 具体日志消息fmt
     * @param params fmt中所需的参数
     */
    public static void debug(String metric, String errorcode, Logger logger, String msg,
                             Object... params) {
        msg = " [monitor][" + metric + "][" + errorcode + "]" + msg;
        debug(logger, msg, params);
    }

    /**
     * 记录日志, 带监控埋点
     * @param metric falcon的监控指标，这里规范为日志记录的“服务类型”，开发可自行定义
     * @param errorcode falcon监控指标metric的细分tag，这里是服务的errorcode状态码，开发可自行定义,如:E_DB_XX、SUCCESS
     * @param logger  Logger实例
     * @param msg 具体日志消息fmt
     * @param params fmt中所需的参数
     */
    public static void info(String metric, String errorcode, Logger logger, String msg,
                            Object... params) {
        msg = " [monitor][" + metric + "][" + errorcode + "]" + msg;
        info(logger, msg, params);
    }

    /**
     * 记录日志, 带监控埋点
     * @param metric falcon的监控指标，这里规范为日志记录的“服务类型”，开发可自行定义
     * @param errorcode falcon监控指标metric的细分tag，这里是服务的errorcode状态码，开发可自行定义,如:E_DB_XX、SUCCESS
     * @param logger  Logger实例
     * @param msg 具体日志消息fmt
     * @param params fmt中所需的参数
     */
    public static void warn(String metric, String errorcode, Logger logger, String msg,
                            Object... params) {
        msg = " [monitor][" + metric + "][" + errorcode + "]" + msg;
        warn(logger, msg, params);
    }

    /**
     * 记录日志, 带监控埋点
     * @param metric falcon的监控指标，这里规范为日志记录的“服务类型”，开发可自行定义
     * @param errorcode falcon监控指标metric的细分tag，这里是服务的errorcode状态码，开发可自行定义,如:E_DB_XX、SUCCESS
     * @param logger  Logger实例
     * @param msg 具体日志消息fmt
     * @param params fmt中所需的参数
     */
    public static void error(String metric, String errorcode, Logger logger, String msg,
                             Object... params) {
        msg = " [monitor][" + metric + "][" + errorcode + "]" + msg;
        error(logger, msg, params);
    }

    /**
     * 记录日志, 带监控埋点
     * @param metric falcon的监控指标，这里规范为日志记录的“服务类型”，开发可自行定义
     * @param errorcode falcon监控指标metric的细分tag，这里是服务的errorcode状态码，开发可自行定义,如:E_DB_XX、SUCCESS
     * @param logger  Logger实例
     * @param msg 具体日志消息fmt
     * @param params fmt中所需的参数
     */
    public static void error(String metric, String errorcode, Logger logger, Throwable e,
                             String msg, Object... params) {
        msg = " [monitor][" + metric + "][" + errorcode + "]" + msg;
        error(logger, e, msg, params);
    }

    /**
     * 异常堆栈转String
     *
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        Writer sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
