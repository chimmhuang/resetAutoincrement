package com.github.chimmhuang.run;

import com.github.chimmhuang.actuator.Actuator;
import com.github.chimmhuang.actuator.ActuatorImpl;

/**
 * Start here
 *
 * @author Chimm Huang
 */
public class ResetAutoincrement {

    public static void main(String[] args) {
        Actuator actuator = new ActuatorImpl();
        // start
        actuator.executeReset();
    }

}
