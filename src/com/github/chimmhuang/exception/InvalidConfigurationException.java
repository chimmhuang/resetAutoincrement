package com.github.chimmhuang.exception;

/**
 * @author Chimm Huang
 */
public class InvalidConfigurationException extends ResetAutoincrementException {

    private static final long serialVersionUID = 8584638948893294857L;

    public InvalidConfigurationException(String message) {
        super(message);
    }

    public InvalidConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
