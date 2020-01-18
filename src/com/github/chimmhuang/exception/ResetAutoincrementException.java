package com.github.chimmhuang.exception;

/**
 * Common Exception
 *
 * @author Chimm Huang
 */
public class ResetAutoincrementException extends RuntimeException {

    private static final long serialVersionUID = 2134897173131329062L;

    public ResetAutoincrementException(String message) {
        super(message);
    }

    public ResetAutoincrementException(String message, Throwable cause) {
        super(message, cause);
    }
}
