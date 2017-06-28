package com.ybj.mynews.ui.news.presenter;

import com.ybj.mynews.R;
import com.ybj.mynews.app.AppConstant;
import com.ybj.mynews.baserx.RxSubscriber;
import com.ybj.mynews.bean.VideoData;
import com.ybj.mynews.ui.news.contract.VideosListContract;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by 杨阳洋 on 2017/6/19.
 * usg:视频联网请求,有mModel发起请求
 */

public class VideoListPresenter extends VideosListContract.Presenter{

    @Override
    public void onStart() {
        super.onStart();
        mRxManager.on(AppConstant.NEWS_LIST_TO_TOP, new Action1<Object>() {
            @Override
            public void call(Object o) {
                mView.scrolltoTopp();
            }
        });
    }

    @Override
    public void getVideoListDataRequest(String type, int startPage) {
        mRxManager.add(mModel.getVideosListData(type,startPage).subscribe(new RxSubscriber<List<VideoData>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<VideoData> videoDatas) {
                mView.returnVideosListData(videoDatas);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
