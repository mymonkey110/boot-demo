package com.netease.boot.dal;

import java.io.Serializable;

/**
 * 值对象接口
 * Created by jiangwenkang on 16-11-2.
 */
public interface ValueObject<T> extends Serializable {

    boolean isSameWith(T other);
}
