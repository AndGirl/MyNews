package com.ybj.mynews.base;

/**
 * Created by 杨阳洋 on 2017/5/22.
 */

public interface BaseView {

    /*******内嵌加载*******/
    void showLoading(String title);
    void stopLoading();
    void showErrorTip(String msg);

}
