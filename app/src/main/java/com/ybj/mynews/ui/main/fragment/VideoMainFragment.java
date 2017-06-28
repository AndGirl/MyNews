package com.ybj.mynews.ui.main.fragment;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.ybj.mynews.R;
import com.ybj.mynews.app.AppConstant;
import com.ybj.mynews.base.BaseFragment;
import com.ybj.mynews.baserecycler.IRecyclerView;
import com.ybj.mynews.baserecycler.OnLoadMoreListener;
import com.ybj.mynews.baserecycler.OnRefreshListener;
import com.ybj.mynews.baserecycler.uiwidget.LoadMoreFooterView;
import com.ybj.mynews.baserecycler.universaladapter.ViewHolderHelper;
import com.ybj.mynews.baserecycler.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.ybj.mynews.bean.VideoData;
import com.ybj.mynews.commonwidget.LoadingTip;
import com.ybj.mynews.ui.news.contract.VideosListContract;
import com.ybj.mynews.ui.news.model.VideoListModel;
import com.ybj.mynews.ui.news.presenter.VideoListPresenter;

import java.util.List;

import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static com.ybj.mynews.R.id.irc;
import static com.ybj.mynews.R.id.loadedTip;

/**
 * Created by 杨阳洋 on 2017/5/18.
 * usg:视频
 */

public class VideoMainFragment extends BaseFragment<VideoListPresenter, VideoListModel> implements VideosListContract, VideosListContract.View, OnRefreshListener, OnLoadMoreListener {
    @Bind(irc)
    IRecyclerView mIrc;
    @Bind(loadedTip)
    LoadingTip mLoadedTip;
    
    private String mVideoType;
    private int mStartPage=0;
    private CommonRecycleViewAdapter<VideoData> mCommonRecycleViewAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_video_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        if(getArguments() != null) {
            mVideoType = getArguments().getString(AppConstant.VIDEO_TYPE);
        }
        mIrc.setLayoutManager(new LinearLayoutManager(getContext()));//默认是竖直方向
        mCommonRecycleViewAdapter = new CommonRecycleViewAdapter<VideoData>(getContext(), R.layout.item_video_list) {
            @Override
            public void convert(ViewHolderHelper helper, VideoData videoData) {
                helper.setImageRoundUrl(R.id.iv_logo, videoData.getTopicImg());//圆角头像
                helper.setText(R.id.tv_from, videoData.getTopicName());//视屏上传者
                helper.setText(R.id.tv_play_time, String.format(getResources().getString(R.string.video_play_times),
                        String.valueOf(videoData.getPlayCount())));//播放量
                /*设置节操播放器*/
                JCVideoPlayerStandard jcVideoPlayerStandard = helper.getView(R.id.videoplayer);//获取JC的View
                jcVideoPlayerStandard.setUp(
                        videoData.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        TextUtils.isEmpty(videoData.getDescription())?videoData.getTitle()+"":videoData.getDescription());
                jcVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(videoData.getCover()));//设置突变
            }
        };

        mIrc.setAdapter(mCommonRecycleViewAdapter);
        mIrc.setOnRefreshListener(this);//设置下拉刷新
        mIrc.setOnLoadMoreListener(this);//设置上拉加载更多

        //item状态改变的监听
        mIrc.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(android.view.View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(android.view.View view) {
                        JCVideoPlayer.releaseAllVideos();
            }
        });

        /*数据为空才重新发起请求*/
        if(mCommonRecycleViewAdapter.getSize() <= 0) {
            //发起请求
            mStartPage = 0;
            mPresenter.getVideoListDataRequest(mVideoType, mStartPage);
        }
        
    }

    @Override
    public void returnVideosListData(List<VideoData> newsVideoList) {
        if(newsVideoList != null) {
            mStartPage += 1;
            if(mCommonRecycleViewAdapter.getPageBean().isRefresh()) {
                mIrc.setRefreshing(false);
                mCommonRecycleViewAdapter.replaceAll(newsVideoList);
            }else{
                if(newsVideoList.size() > 0) {
                    mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    mCommonRecycleViewAdapter.addAll(newsVideoList);
                }else {
                    mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void scrolltoTopp() {
        mIrc.smoothScrollToPosition(0);
    }

    @Override
    public void showLoading(String title) {
        if( mCommonRecycleViewAdapter.getPageBean().isRefresh()) {
            if(mCommonRecycleViewAdapter.getSize()<=0) {
                mLoadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    @Override
    public void stopLoading() {
        mLoadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if( mCommonRecycleViewAdapter.getPageBean().isRefresh()) {
            if(mCommonRecycleViewAdapter.getSize()<=0) {
                mLoadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                mLoadedTip.setTips(msg);
                mIrc.setRefreshing(false);
            }
        }else{
            mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void onRefresh() {
        mCommonRecycleViewAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        mIrc.setRefreshing(true);
        mPresenter.getVideoListDataRequest(mVideoType,mStartPage);
    }

    @Override
    public void onLoadMore(android.view.View loadMoreView) {
        mCommonRecycleViewAdapter.getPageBean().setRefresh(false);
        //发起请求
        mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getVideoListDataRequest(mVideoType,mStartPage);
    }
}
