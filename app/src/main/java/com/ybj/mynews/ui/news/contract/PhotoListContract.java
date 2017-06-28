package com.ybj.mynews.ui.news.contract;

import com.ybj.mynews.base.BaseModel;
import com.ybj.mynews.base.BasePresenter;
import com.ybj.mynews.base.BaseView;
import com.ybj.mynews.bean.PhotoGril;

import java.util.List;

import rx.Observable;

/**
 * Created by 杨阳洋 on 2017/5/22.
 */


public interface PhotoListContract {
    interface Model extends BaseModel{
        //请求获取图片
        Observable<List<PhotoGril>> getPhotosListData(int size , int page);
    }

    interface View extends BaseView {
        //返回获取的图片
        void returnPhotosListData(List<PhotoGril> photoGirls);
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        //发起获取图片请求
        public abstract void getPhotosListDataRequest(int size, int page);
    }

}
