package com.github.chimmhuang.validation;

import com.github.chimmhuang.config.ConnectionConfig;
import com.github.chimmhuang.exception.InvalidConfigurationException;
import org.apache.log4j.Logger;

/**
 * @author Chimm Huang
 */
public class ConnectionValidator {

    private static Logger logger = Logger.getLogger(ConnectionValidator.class);

    private ConnectionValidator() { }

    public static void checkConnectionConfig() {
        ConnectionConfig connectionConfig = ConnectionConfig.getInstance();
        if (connectionConfig.getClassDriver() == null || "".equals(connectionConfig.getClassDriver().trim()))
            throw new InvalidConfigurationException("classDriver can not be empty");

        if (connectionConfig.getUrl() == null || "".equals(connectionConfig.getUrl().trim()))
            throw new InvalidConfigurationException("url can not be empty");

        if (connectionConfig.getUsername() == null || "".equals(connectionConfig.getUsername().trim()))
            throw new InvalidConfigurationException("username can not be empty");

        logger.info("------------------ connectionConfig info ------------------");
        logger.info("            classDriver: " + connectionConfig.getClassDriver());
        logger.info("            url: " + connectionConfig.getUrl());
        logger.info("            username: " + connectionConfig.getUsername());
        logger.info("------------------ connectionConfig info ------------------");

    }
}
