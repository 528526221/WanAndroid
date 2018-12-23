package com.xulc.wanandroid.utils;

import android.os.Environment;
import android.util.Log;

import com.xulc.wanandroid.net.Constant;
import com.xulc.wanandroid.net.DownloadService;
import com.xulc.wanandroid.net.RetrofitManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Date：2018/11/20
 * Author: xuliangchun
 * Desc: 文件下载工具类
 */
public class DownloadUtil {
    private static final String DOWNLOAD_DIR_NAME = "/wanandroid/download/";//下载文件保存目录
    private static DownloadService downloadService;
    private DownloadUtil() { }

    private static class SingletonInstance {
        private static final DownloadUtil INSTANCE = new DownloadUtil();
    }

    public static DownloadUtil getInstance(){
        return SingletonInstance.INSTANCE;
    }

    /**
     * 获取下载接口类
     * @return DownloadService
     */
    private DownloadService getDownloadService(){
        if (downloadService == null){
            synchronized (RetrofitManager.class){
                if (downloadService == null){
                    downloadService  = RetrofitManager.create(DownloadService.class);
                }
            }
        }
        return downloadService;
    }

    /**
     * 下载文件
     * @param url 网络文件地址全路径
     * @param fileName 保存到本地的文件名称
     * @param listener 下载监听回调
     */
    public void downloadFile(String url, final String fileName, final DownloadListener listener) {
        getDownloadService().downloadFile(url).observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.onStart();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        saveResponseToFile(fileName, responseBody, listener);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    /**
     * 保存响应到文件
     * @param fileName
     * @param responseBody
     * @param listener
     */
    private void saveResponseToFile(String fileName, ResponseBody responseBody, DownloadListener listener) {
        File mFile = new File(Environment.getExternalStorageDirectory() + DOWNLOAD_DIR_NAME + fileName);
        if (mFile.getParentFile().exists()){
            if (mFile.exists() && mFile.delete()){
                Log.i(Constant.TAG,"删除历史文件成功.");
            }
        }else {
            if (mFile.getParentFile().mkdirs()){
                Log.i(Constant.TAG,"创建目录成功.");
            }else {
                Log.i(Constant.TAG,"创建目录失败.");
                listener.onFail("创建目录失败");
                return;
            }
        }
        int sBufferSize = 2048;
        //从response获取输入流以及总大小
        InputStream is = responseBody.byteStream();
        OutputStream os = null;
        try {
            if (mFile.createNewFile()){
                long currentLength = 0;
                os = new BufferedOutputStream(new FileOutputStream(mFile));
                byte[] bytes = new byte[sBufferSize];
                int len;
                while ((len = is.read(bytes,0,sBufferSize)) != -1){
                    os.write(bytes,0,len);
                    currentLength += len;
                    //计算当前下载进度
                    listener.onProgress((int) (100 * currentLength / responseBody.contentLength()));
                }
                //下载完成，并返回保存的文件路径
                listener.onFinish(mFile.getAbsolutePath());
            }else {
                listener.onFail("创建文件失败");
                Log.i(Constant.TAG,"创建文件失败.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            listener.onFail(e.getMessage());
            Log.i(Constant.TAG,"写入文件IO出错.");
        } finally {
            try {
                is.close();
                if (os != null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public interface DownloadListener {
        void onStart();//下载开始

        void onProgress(int progress);//下载进度

        void onFinish(String path);//下载完成

        void onFail(String message);//下载失败
    }
}
