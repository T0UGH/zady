package com.edu.neu.zady.exception;

public class NoAuthException extends RuntimeException {
    public NoAuthException(String msg){
        super(msg);
    }
}
