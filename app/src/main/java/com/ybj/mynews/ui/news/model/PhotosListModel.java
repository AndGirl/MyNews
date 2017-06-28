package com.ybj.mynews.ui.news.model;

import com.ybj.mynews.api.Api;
import com.ybj.mynews.api.HostType;
import com.ybj.mynews.baserx.RxSchedulers;
import com.ybj.mynews.bean.GirlData;
import com.ybj.mynews.bean.PhotoGril;
import com.ybj.mynews.ui.news.contract.PhotoListContract;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by 杨阳洋 on 2017/5/22.
 * 图片数据层
 */

public class PhotosListModel implements PhotoListContract.Model{
    @Override
    public Observable<List<PhotoGril>> getPhotosListData(int size, int page) {
        return Api.getDefault(HostType.GANK_GIRL_PHOTO)
                .getPhotoList(Api.getCacheControl(),size,page)
                .map(new Func1<GirlData, List<PhotoGril>>() {
                    @Override
                    public List<PhotoGril> call(GirlData girlData) {
                        return girlData.getResults();
                    }
                })
                .compose(RxSchedulers.<List<PhotoGril>>io_main());
    }
}
