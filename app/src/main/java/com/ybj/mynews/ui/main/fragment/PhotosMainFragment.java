package com.ybj.mynews.ui.main.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ybj.mynews.R;
import com.ybj.mynews.base.BaseFragment;
import com.ybj.mynews.baserecycler.IRecyclerView;
import com.ybj.mynews.baserecycler.OnLoadMoreListener;
import com.ybj.mynews.baserecycler.OnRefreshListener;
import com.ybj.mynews.baserecycler.uiwidget.LoadMoreFooterView;
import com.ybj.mynews.baserecycler.universaladapter.ViewHolderHelper;
import com.ybj.mynews.baserecycler.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.ybj.mynews.bean.PhotoGril;
import com.ybj.mynews.commonwidget.LoadingTip;
import com.ybj.mynews.commonwidget.NormalTitleBar;
import com.ybj.mynews.ui.main.activity.PhotosDetailActivity;
import com.ybj.mynews.ui.news.contract.PhotoListContract;
import com.ybj.mynews.ui.news.model.PhotosListModel;
import com.ybj.mynews.ui.news.presenter.PhotosListPresenter;

import java.util.List;

import butterknife.Bind;


/**
 * Created by 杨阳洋 on 2017/5/18.
 * usg:图片首页,采用MVP模式
 */

public class PhotosMainFragment extends BaseFragment<PhotosListPresenter,PhotosListModel> implements PhotoListContract.View ,OnRefreshListener,OnLoadMoreListener{
    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private CommonRecycleViewAdapter<PhotoGril>adapter;
    private static int SIZE = 20;
    private int mStartPage = 1;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_photo_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        ntb.setTvLeftVisiable(false);
        ntb.setTitleText(getString(R.string.girl_title));
        /*CommonRecycleViewAdapter普通的RecyclerView*/
        adapter=new CommonRecycleViewAdapter<PhotoGril>(getContext(),R.layout.item_photo) {
            @Override
            public void convert(ViewHolderHelper helper,final PhotoGril photoGirl) {
                ImageView imageView=helper.getView(R.id.iv_photo);
                Glide.with(mContext).load(photoGirl.getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.ic_image_loading)
                        .error(R.drawable.ic_empty_picture)
                        .centerCrop().override(1090, 1090*3/4)
                        .crossFade().into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotosDetailActivity.startAction(mContext,photoGirl.getUrl());
                    }
                });
            }
        };
        irc.setAdapter(adapter);
        irc.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        irc.setOnLoadMoreListener(this);
        irc.setOnRefreshListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irc.smoothScrollToPosition(0);
            }
        });
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);
    }

    @Override
    public void returnPhotosListData(List<PhotoGril> photoGirls) {
        if (photoGirls != null) {
            mStartPage +=1;
            if (adapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                adapter.replaceAll(photoGirls);
            } else {
                if (photoGirls.size() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    adapter.addAll(photoGirls);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void showLoading(String title) {
        if(adapter.getPageBean().isRefresh())
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if( adapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            irc.setRefreshing(false);
        }else{
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void onRefresh() {
        adapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);
    }
    @Override
    public void onLoadMore(View loadMoreView) {
        adapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);
    }

}
