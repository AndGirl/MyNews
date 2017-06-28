package com.ybj.mynews.commonwidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ybj.mynews.R;
import com.ybj.mynews.commonutils.DisplayUtil;

/**
 * Created by 杨阳洋 on 2017/6/1.
 * usg:公共的状态栏
 */

public class NormalTitleBar extends RelativeLayout {

    private ImageView ivRight;
    private TextView ivBack,tvTitle,tvRight;
    private RelativeLayout rlCommonTitle;
    private Context context;

    public NormalTitleBar(Context context) {
        super(context);
        this.context = context;
    }

    public NormalTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.bar_normal,this);
        ivBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ivRight = (ImageView) findViewById(R.id.image_right);
        rlCommonTitle = (RelativeLayout) findViewById(R.id.common_title);
    }

    public void setHeaderHeight(){
        //布局参数发生改变的时候适合调用requestLayout（）方法
        rlCommonTitle.setPadding(0, DisplayUtil.getStatusBarHeight(context),0,0);
        rlCommonTitle.requestLayout();
    }

    /**
     * 管理返回按钮
     * @param visibility
     */
    public void setBackVisibility(boolean visibility){
        if(visibility) {
            ivBack.setVisibility(VISIBLE);
        }else{
            ivBack.setVisibility(GONE);
        }
    }

    /**
     * 设置标题栏左侧字符串
     * @param tvLeft
     */
    public void setTvLeft(String tvLeft){
        ivBack.setText(tvLeft);
    }

    /**
     * 设置标题栏左侧字符串
     * @param visiable
     */
    public void setTvLeftVisiable(boolean visiable){
        if (visiable){
            ivBack.setVisibility(View.VISIBLE);
        }else{
            ivBack.setVisibility(View.GONE);
        }
    }

    /**
     * 管理标题
     */
    public void setTitleVisibility(boolean visible) {
        if (visible) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    public void setTitleText(String string) {
        tvTitle.setText(string);
    }

    public void setTitleText(int string) {
        tvTitle.setText(string);
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    /**
     * 右图标
     * @param visibility
     */
    public void setRightImageVisibility(boolean visibility){
        ivRight.setVisibility(visibility ? VISIBLE : GONE);
    }

    public void setRightImagSrc(int id) {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(id);
    }

    /**
     * 左图标
     *
     * @param id
     */
    public void setLeftImagSrc(int id) {
        ivBack.setCompoundDrawables(getResources().getDrawable(id),null,null,null);
    }
    /**
     * 左文字
     *
     * @param str
     */
    public void setLeftTitle(String str) {
        ivBack.setText(str);
    }

    /**
     * 右标题
     */
    public void setRightTitleVisibility(boolean visible) {
        tvRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightTitle(String text) {
        tvRight.setText(text);
    }


    /*
     * 点击事件
     */
    public void setOnBackListener(OnClickListener listener) {
        ivBack.setOnClickListener(listener);
    }

    public void setOnRightImagListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    public void setOnRightTextListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    /**
     * 标题背景颜色
     * @param color
     */
    public void setBackGroudColor(int color){
        rlCommonTitle.setBackgroundColor(color);
    }

    public Drawable getBackGroudDrawable(){
        return rlCommonTitle.getBackground();
    }

}
