package com.xulc.wanandroid.utils;

import com.xulc.wanandroid.base.BaseObserver;
import com.xulc.wanandroid.base.BaseResponse;
import com.xulc.wanandroid.bean.UploadImageData;
import com.xulc.wanandroid.net.FileRequestBody;
import com.xulc.wanandroid.net.RetrofitManager;
import com.xulc.wanandroid.net.UploadService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Date：2018/11/20
 * Author: xuliangchun
 * Desc: 文件上传工具类
 */
public class UploadUtil {
    private static UploadService uploadService;
    private UploadUtil(){}
    private static class SingletonInstance {
        private static final UploadUtil INSTANCE = new UploadUtil();
    }
    public static UploadUtil getInstance(){
        return SingletonInstance.INSTANCE;
    }
    /**
     * 获取上传接口类
     * @return UploadService
     */
    private UploadService getUploadService(){
        if (uploadService == null){
            synchronized (RetrofitManager.class){
                if (uploadService == null){
                    uploadService  = RetrofitManager.create(UploadService.class);
                }
            }
        }
        return uploadService;
    }

    public void uploadImage(String filePath, final UploadListener listener){
        File file = new File(filePath);
        //表单上传
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        FileRequestBody fileRequestBody = new FileRequestBody(requestBody,listener);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
        listener.onStart();
        getUploadService().uploadImage(part)
                .compose(RxSchedulers.<BaseResponse<UploadImageData>>applySchedulers())
                .subscribe(new BaseObserver<UploadImageData>() {
                    @Override
                    protected void onSuccess(BaseResponse<UploadImageData> uploadImageDataBaseResponse) {
                        listener.onFinish(uploadImageDataBaseResponse.getData().getPhotoUrl());
                    }

                    @Override
                    protected void onFail(BaseResponse<UploadImageData> uploadImageDataBaseResponse) {
                        listener.onFail(uploadImageDataBaseResponse.getErrorMsg());
                    }
                });



    }

    public interface UploadListener {
        void onStart();//上传开始

        void onProgress(int progress);//上传进度

        void onFinish(String photoUrl);//上传完成

        void onFail(String message);//上传失败
    }
}
