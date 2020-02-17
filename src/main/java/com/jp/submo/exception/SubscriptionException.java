package com.jp.submo.exception;

/**
 * @author chetan
 */
public class SubscriptionException extends RuntimeException {

    public SubscriptionException(String message) {
        super(message);
    }

    public static void throwError(String message){
        throw new SubscriptionException(message);
    }
}
