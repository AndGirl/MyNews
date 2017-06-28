package com.ybj.mynews.ui.news.model;

import com.ybj.mynews.api.Api;
import com.ybj.mynews.api.HostType;
import com.ybj.mynews.baserx.RxSchedulers;
import com.ybj.mynews.bean.VideoData;
import com.ybj.mynews.commonutils.TimeUtil;
import com.ybj.mynews.ui.news.contract.VideosListContract;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by 杨阳洋 on 2017/6/19.
 * usg：视频层数据
 */

public class VideoListModel implements VideosListContract.Model{
    @Override
    public Observable<List<VideoData>> getVideosListData(final String type, int startPage) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO)
                .getVideoList(Api.getCacheControl(),type,startPage)
                .flatMap(new Func1<Map<String, List<VideoData>>, Observable<VideoData>>() {
                    @Override
                    public Observable<VideoData> call(Map<String, List<VideoData>> stringListMap) {
                        return Observable.from(stringListMap.get(type));
                    }
                })
                //转化时间
                .map(new Func1<VideoData, VideoData>() {
                    @Override
                    public VideoData call(VideoData videoData) {
                        String ptime = TimeUtil.formatDate(videoData.getPtime());
                        videoData.setPtime(ptime);
                        return videoData;
                    }
                })
                .distinct()//去重
                .toSortedList(new Func2<VideoData, VideoData, Integer>() {//排序
                    @Override
                    public Integer call(VideoData videoData, VideoData videoData2) {
                        return videoData2.getPtime().compareTo(videoData.getPtime());
                    }
                })
                .compose(RxSchedulers.<List<VideoData>>io_main());
    }
}
