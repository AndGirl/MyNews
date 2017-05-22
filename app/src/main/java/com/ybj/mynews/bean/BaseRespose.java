package com.ybj.mynews.bean;

import java.io.Serializable;

/**
 * Created by 杨阳洋 on 2017/5/22.
 */

public class BaseRespose <T> implements Serializable{
    public String code;
    public String msg;

    public T data;

    public boolean success() {
        return "1".equals(code);
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
