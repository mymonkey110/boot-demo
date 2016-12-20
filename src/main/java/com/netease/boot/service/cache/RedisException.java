package com.netease.boot.service.cache;

/**
 * Redis Exception
 * Created by Michael Jiang on 15/8/4.
 */
public class RedisException extends RuntimeException {
    private static final long serialVersionUID = 7776981258552655485L;

    public RedisException() {
        super();
    }

    public RedisException(String s) {
        super(s);
    }

    public RedisException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RedisException(Throwable throwable) {
        super(throwable);
    }
}
