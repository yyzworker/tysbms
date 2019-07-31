package com.tys.exception;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-4-26 9:34
 */
public class NoSessionException extends RuntimeException {

    public NoSessionException() {
    }

    public NoSessionException(String message) {
        super(message);
    }

}
