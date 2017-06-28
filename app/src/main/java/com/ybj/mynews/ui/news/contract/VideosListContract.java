package com.ybj.mynews.ui.news.contract;

import com.ybj.mynews.base.BaseModel;
import com.ybj.mynews.base.BasePresenter;
import com.ybj.mynews.base.BaseView;
import com.ybj.mynews.bean.VideoData;

import java.util.List;

import rx.Observable;

/**
 * Created by 杨阳洋 on 2017/6/19.
 * usg:视频列表
 */

public interface VideosListContract {
    interface Model extends BaseModel{
        //请求获取视频
        Observable <List<VideoData>> getVideosListData(String type,
                                                       int startPage);
    }

    interface View extends BaseView {
        //返回获取的视频
        void returnVideosListData(List<VideoData> newsVideoList);
        //返回顶部
        void scrolltoTopp();
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        //发起获取视频请求
        public abstract void getVideoListDataRequest(String type,int startPage);
    }
}
