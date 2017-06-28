package com.ybj.mynews.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;


/**
 * Created by 杨阳洋 on 2017/6/14.
 * usg:下拉退出当前frameLayout
 *  创建实例
    触摸相关的方法的调用
    ViewDragHelper.Callback实例的编写
 */

public class PullBackLayout extends FrameLayout {
    /*自定义ViewGroup时候的帮助类*/
    private final ViewDragHelper dragger;
    private final int minmumFlingVelocity;

    @Nullable
    private Callback callback;

    public PullBackLayout(Context context) {
        this(context,null);
    }

    public PullBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PullBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragger = ViewDragHelper.create(this,1f/8f,new ViewDragCallback() );
        minmumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    /*自定义回调的监听*/
    public interface Callback{
        void onPullStart();
        void onPull(float progress);
        void onPullCancel();
        void onPullComplete();
    }

    public void setCallback(@Nullable Callback callback){
        this.callback = callback;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return dragger.shouldInterceptTouchEvent(ev);//是否拦截事件
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            dragger.processTouchEvent(event);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /*然后通过方法ViewCompat.postInvalidateOnAnimation(this)调用view的 computeScroll() 方法：*/
    @Override
    public void computeScroll() {
        if(dragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class ViewDragCallback extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {//当前view是否允许拖动
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {//返回横向坐标左右边界值
            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {//返回纵向坐标左右边界值
            return Math.max(0,top);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {//横向拖动的最大距离</span>
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {//横向竖直的最大距离</span>
            return getHeight();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            if(callback != null) {
                callback.onPullStart();
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {//view在拖动过程坐标发生变化时会调用此方法，包括两个时间段：手动拖动和自动滚动
            if(callback != null) {
                callback.onPull((float) top / (float) getHeight());
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int slop = yvel > minmumFlingVelocity ? getHeight() / 6 : getHeight() / 3;//ACTION_UP事件后调用其方法
            if(releasedChild.getTop() > slop) {
                if(callback != null) {
                    callback.onPullComplete();
                }
            }else{
                if(callback != null) {
                    callback.onPullCancel();
                }
                //mAutoBackView手指释放时可以自动回去
                dragger.settleCapturedViewAt(0,0);
                invalidate();
            }
        }
    }

}
