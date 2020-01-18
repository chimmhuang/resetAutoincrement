package com.github.chimmhuang.run;

import com.github.chimmhuang.actuator.Actuator;
import com.github.chimmhuang.actuator.ActuatorImpl;
import com.github.chimmhuang.config.ConnectionConfig;
import com.github.chimmhuang.validation.ConnectionValidator;

/**
 * Start here
 *
 * @author Chimm Huang
 */
public class ResetAutoincrement {

    public static void main(String[] args) {
        ConnectionValidator.checkConnectionConfig();
        Actuator actuator = new ActuatorImpl(ConnectionConfig.getInstance());
        // start
        actuator.executeReset();
    }

}
