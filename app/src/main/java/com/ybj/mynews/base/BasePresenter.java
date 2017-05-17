package com.ybj.mynews.base;

import android.content.Context;

import com.ybj.mynews.baserx.RxManager;

/**
 * Created by 杨阳洋 on 2017/5/14.
 * 作用：用于处理RxJava
 */

public abstract class BasePresenter<T, E> {
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManager = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart(){
    };
    public void onDestroy() {
        mRxManager.clear();
    }

}
