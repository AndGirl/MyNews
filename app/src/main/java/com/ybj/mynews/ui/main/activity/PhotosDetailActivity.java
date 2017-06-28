package com.ybj.mynews.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ybj.mynews.R;
import com.ybj.mynews.app.AppConstant;
import com.ybj.mynews.commonutils.MyUtils;
import com.ybj.mynews.commonutils.SystemUiVisibilityUtil;
import com.ybj.mynews.commonwidget.StatusBarCompat;
import com.ybj.mynews.widget.PullBackLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 大图详情
 */

public class PhotosDetailActivity extends AppCompatActivity implements PullBackLayout.Callback {

    @Bind(R.id.photo_touch_iv)
    PhotoView mPhotoTouchIv;
    @Bind(R.id.pull_back_layout)
    PullBackLayout mPullBackLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.activity_photos_detail)
    RelativeLayout mActivityPhotosDetail;

    private boolean mIsToolBarHidden;//toolbar是否隐藏
    private boolean mIsStatusBarHidden;//toolbar隐藏状态栏
    private ColorDrawable mBackground;//背景图片

    public static void startAction(Context context,String url){
        Intent intent = new Intent(context, PhotosDetailActivity.class);
        intent.putExtra(AppConstant.PHOTO_DETAIL,url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        StatusBarCompat.translucentStatusBar(this);
        setContentView(R.layout.activity_photos_detail);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initView() {
        mPullBackLayout.setCallback(this);
        toolBarFadeIn();
        initToolbar();
        initBackground();
        loadPhotoIv();
        initImageView();
        setPhotoViewClickEvent();
    }

    /*设置图片的点击事件*/
    private void setPhotoViewClickEvent() {
        mPhotoTouchIv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                hideOrShowToolbar();
                hideOrShowStatusBar();
            }
        });
    }
    
    /*影藏状态栏*/
    private void hideOrShowStatusBar() {
        if(mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(PhotosDetailActivity.this);
        }else{
            SystemUiVisibilityUtil.exit(PhotosDetailActivity.this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }

    private void initImageView() {
        loadPhotoIv();
    }

    private void loadPhotoIv() {
        String url = getIntent().getStringExtra(AppConstant.PHOTO_DETAIL);
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_empty_picture)
                .crossFade().into(mPhotoTouchIv);
    }

    private void initBackground() {
        mBackground = new ColorDrawable(Color.BLACK);
        MyUtils.getRootView(this).setBackgroundDrawable(mBackground);
    }

    private void initToolbar() {
        mToolbar.setTitle("美女");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {//返回键的监听
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void toolBarFadeIn() {
        mIsToolBarHidden = true;
        hideOrShowToolbar();
    }

    private void hideOrShowToolbar() {
        mToolbar.animate()
                .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
    }

    private void toolBarFadeOut() {
        mIsToolBarHidden = false;
        hideOrShowToolbar();
    }

    @Override
    public void onPullStart() {
        toolBarFadeOut();
        mIsStatusBarHidden = true;
        hideOrShowStatusBar();
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f,progress*3f);
        mBackground.setAlpha((int)(0xff/*255*/ * (1f-progress)));
    }

    @Override
    public void onPullCancel() {
        toolBarFadeIn();
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();//暂时理解为退出时结束当前activity
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }
}
