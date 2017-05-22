package com.ybj.mynews.ui.news.model;

import com.ybj.mynews.bean.PhotoGril;
import com.ybj.mynews.ui.news.contract.PhotoListContract;

import java.util.List;

import rx.Observable;

/**
 * Created by 杨阳洋 on 2017/5/22.
 * 图片数据层
 */

public class PhotosListModel implements PhotoListContract.Model{
    @Override
    public Observable<List<PhotoGril>> getPhotosListData(int size, int page) {
        return null;
    }
}
