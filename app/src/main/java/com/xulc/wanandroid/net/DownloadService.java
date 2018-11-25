package com.xulc.wanandroid.net;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Date：2018/11/19
 * Author: xuliangchun
 * Desc: 下载接口类
 * 请直接使用
 * @see hikvision.com.hik_base.utils.DownloadUtil 下载
 */
public interface DownloadService {
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);//下载文件
}
