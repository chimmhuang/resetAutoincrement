package com.github.chimmhuang.exception;

/**
 * @author Chimm Huang
 */
public class NoDatabaseTableException extends ResetAutoincrementException {

    private static final long serialVersionUID = -5372594254618241033L;

    public NoDatabaseTableException(String message) {
        super(message);
    }

    public NoDatabaseTableException(String message, Throwable cause) {
        super(message, cause);
    }
}
