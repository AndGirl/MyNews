package com.ybj.mynews.daynightmodeutils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 杨阳洋 on 2017/5/17.
 * usg:夜间模式辅助器
 */

public class ChangeModeHelper {
    public static final int MODE_DAY = 1;

    public static final int MODE_NIGHT = 2;
    private static String Mode = "mode";

    public static void setChangeMode(Context ctx,int mode){
        SharedPreferences sp = ctx.getSharedPreferences("config_mode", Context.MODE_PRIVATE);
        sp.edit().putInt(Mode,mode).commit();
    }

    public static int getChangeMode(Context ctx){
        SharedPreferences sp = ctx.getSharedPreferences("config_mode", Context.MODE_PRIVATE);
        return sp.getInt(Mode,MODE_DAY);
    }

}
