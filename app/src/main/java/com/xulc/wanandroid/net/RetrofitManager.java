package com.xulc.wanandroid.net;


import com.blankj.utilcode.util.NetworkUtils;
import com.xulc.wanandroid.base.App;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class RetrofitManager {
    private static long CONNECT_TIMEOUT = 60L;//连接超时
    private static long READ_TIMEOUT = 10L;//读超时
    private static long WRITE_TIMEOUT = 10L;//写超时
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;//缓存记录有效期1天
    private static final int CACHE_NET_SEC = 10;//在10s内的GET请求不会重复向网络提交，会优先从缓存中读取
    private static volatile OkHttpClient mOkHttpClient;

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            //http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html
            //为了缓存响应，你需要一个你可以读写的缓存目录，和缓存大小的限制。这个缓存目录应该是私有的，不信任的程序应不能读取缓存内容。
            //响应缓存使用HTTP头作为配置。你可以在请求头中添加Cache-Control: max-stale=3600 ,OkHttp缓存会支持。你的服务通过响应头确定响应缓存多长时间，例如使用Cache-Control: max-age=9600。

            Request request = chain.request();
            if (NetworkUtils.isConnected()){
                request = request.newBuilder()
                       .cacheControl(new CacheControl.Builder().maxStale(CACHE_NET_SEC,TimeUnit.SECONDS).build())
//                        .addHeader("Cache-Control","max-stale=10")
                        .build();
                Response response = chain.proceed(request);

                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public, max-age=" + CACHE_NET_SEC)
                        .build();
            }else {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Response response = chain.proceed(request);

                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control","public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .build();
            }


        }
    };

    /**
     * 日志拦截器
     */
    private static final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    };

    /**
     * 获取OkHttpClient实例
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);//缓存文件大小10M
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mLoggingInterceptor)
                            .cookieJar(new CookiesManager())
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }


    /**
     * 获取Service
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.REQUEST_BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        return retrofit.create(clazz);
    }
}
