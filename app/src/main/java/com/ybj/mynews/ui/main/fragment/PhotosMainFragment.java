package com.ybj.mynews.ui.main.fragment;

import com.ybj.mynews.R;
import com.ybj.mynews.base.BaseFragment;
import com.ybj.mynews.ui.news.model.PhotosListModel;

/**
 * Created by 杨阳洋 on 2017/5/18.
 * usg:图片首页,采用MVP模式
 */

public class PhotosMainFragment extends BaseFragment<PhotosListPresenter,PhotosListModel> {
    @Override
    protected int getLayoutResource() {
        return R.layout.fra_photo_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }
}
