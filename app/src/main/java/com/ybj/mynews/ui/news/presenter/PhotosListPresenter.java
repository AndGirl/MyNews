package com.ybj.mynews.ui.news.presenter;

import com.ybj.mynews.R;
import com.ybj.mynews.baserx.RxSubscriber;
import com.ybj.mynews.bean.PhotoGril;
import com.ybj.mynews.ui.news.contract.PhotoListContract;

import java.util.List;

/**
 * Created by 杨阳洋 on 2017/5/23.
 * usg:图片列表,联网(由mModel发起数据请求)
 */

public class PhotosListPresenter extends PhotoListContract.Presenter{
    @Override
    public void getPhotosListDataRequest(int size, int page) {
       mRxManager.add(mModel.getPhotosListData(size,page).subscribe(new RxSubscriber<List<PhotoGril>>(mContext,false) {
           @Override
           public void onStart() {
               super.onStart();
               mView.showLoading(mContext.getString(R.string.loading));
           }

           @Override
           protected void _onNext(List<PhotoGril> photoGrils) {
                mView.returnPhotosListData(photoGrils);
               mView.stopLoading();
           }

           @Override
           protected void _onError(String message) {
                mView.showErrorTip(mContext.getString(R.string.net_error));
           }
       }));
    }
}
