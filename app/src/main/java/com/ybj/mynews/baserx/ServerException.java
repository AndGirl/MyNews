package com.ybj.mynews.baserx;

/**
 * Created by 杨阳洋 on 2017/5/23.
 * usg:服务器请求异常
 */

public class ServerException extends Exception{
    public ServerException(String msg){
        super(msg);
    }
}
