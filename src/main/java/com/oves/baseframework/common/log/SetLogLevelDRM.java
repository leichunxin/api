package com.oves.baseframework.common.log;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oves.baseframework.common.drm.AppDrmNode;
import com.oves.baseframework.common.drm.DrmZookeeprClient;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 推送变更日志级别
 *
 * @author jin.qian
 * @version $Id: SetLogLevelDRM.java, v 0.1 2016年1月22日 下午9:32:46 jin.qian Exp $
 */
public class SetLogLevelDRM {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(SetLogLevelDRM.class);

    private String logLevelConfig = "INFO";

    private Set<String> acceptLevels = new HashSet<String>(Arrays.asList("TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL"));

    private DrmZookeeprClient drmZookeeprClient;

    public void init() {
        AppDrmNode logLevelNode = new AppDrmNode(this, "logLevelConfig", logLevelConfig);
        drmZookeeprClient.confRegist(logLevelNode, true);
    }

    /**
     * <pre>
     * 接受推送过来的日志名及响应的级别，找到合适的Logger，设置为推送过来的级别
     * 内容格式说明：
     * LoggerName1:Level1
     * LoggerName2:Level2
     *
     * logger名字和level之间可以使用冒号或者等号
     * 每条logger配置之间可以用换行或者分号或者逗号分割
     * </pre>
     *
     * @param logLevelConfig
     */
    public void setLogLevelConfig(String logLevelConfig) {
        if (logger.isInfoEnabled()) {
            logger.info("接收到推送的日志配置：" + logLevelConfig);
        }
        String[] pairs = StringUtils.split(logLevelConfig, ",;\n\r");
        if (pairs != null) {
            Arrays.sort(pairs);
            for (String nameAndLevel : pairs) {
                String[] nameLevelPair = StringUtils.split(nameAndLevel, ":=");
                if (nameLevelPair != null && nameLevelPair.length >= 2) {
                    String loggerName = StringUtils.trim(nameLevelPair[0]);
                    String levelName = StringUtils.trim(nameLevelPair[1]);
                    setLogLevel(loggerName, StringUtils.upperCase(levelName));
                } else {
                    if (logger.isInfoEnabled()) {
                        logger.info("Unrecognized format [" + nameAndLevel + "]");
                    }
                }
            }
        }
        this.logLevelConfig = logLevelConfig;
    }

    private void setLogLevel(String loggerName, String level) {
        org.apache.log4j.Logger log = null;
        if ("root".equalsIgnoreCase(loggerName)) {
            log = LogManager.getRootLogger();
        } else if (StringUtils.isNotBlank(loggerName)) {
            log = org.apache.log4j.Logger.getLogger(loggerName);
        }

        if (log != null && StringUtils.isNotBlank(level)) {
            if (acceptLevels.contains(level.toUpperCase())) {
                if (logger.isInfoEnabled()) {
                    logger.info("Set logger [" + log.getName() + "] to [" + level + "]");
                }
                log.setLevel(Level.toLevel(level.toUpperCase()));
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("Unaccept level [" + level + "]");
                }
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger
                        .info("Logger [" + loggerName + "] is null or level [" + level + "] is blank");
            }
        }
    }

    public String getLogLevelConfig() {
        return logLevelConfig;
    }

    public void setDrmZookeeprClient(DrmZookeeprClient drmZookeeprClient) {
        this.drmZookeeprClient = drmZookeeprClient;
    }
}
