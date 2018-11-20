package com.xulc.wanandroid.net;

import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.UploadImageData;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Date：2018/11/20
 * Author: xuliangchun
 * Desc: 文件上传接口
 * 请直接使用
 * @see com.xulc.wanandroid.utils.UploadUtil 上传
 */
public interface UploadService {
    @Multipart
    @POST("sn/mobileuploadimage")
    Observable<BaseResponse<UploadImageData>> uploadImage(@Part MultipartBody.Part part);//上传文件
}
