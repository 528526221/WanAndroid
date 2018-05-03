package com.xulc.wanandroid.net;

import com.xulc.wanandroid.bean.ArticleData;
import com.xulc.wanandroid.bean.Banner;
import com.xulc.wanandroid.bean.BaseResponse;
import com.xulc.wanandroid.bean.KnowledgeSystem;
import com.xulc.wanandroid.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Date：2018/4/11
 * Desc：玩android开放api
 * http://www.wanandroid.com/blog/show/2
 * Created by xuliangchun.
 */

public interface ApiService {
    //1.首页相关
    /**
     * 1.1首页文章列表
     * @param page 页码，拼接在连接中，从0开始。
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<BaseResponse<ArticleData>> getHomeArticles(@Path("page") int page);

    /**
     * 1.2首页banner
     * @return
     */
    @GET("/banner/json")
    Observable<BaseResponse<List<Banner>>> getHomeBanners();

    //2.知识体系

    /**
     * 2.1 体系数据
     * @return
     */
    @GET("/tree/json")
    Observable<BaseResponse<List<KnowledgeSystem>>> getKnowledgeSystem();


    /**
     * 2.2 知识体系下的文章
     * @param page 页码：拼接在链接上，从0开始。
     * @param cid 分类的id，上述二级目录的id
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<BaseResponse<ArticleData>> getKnowledgeArticles(@Path("page") int page,@Query("cid") int cid);

    //5.登录与注册

    /**
     * 5.1 登录
     * @param username
     * @param password
     * @return
     */
    @POST("/user/login")
    @FormUrlEncoded
    Observable<BaseResponse<User>> loginAccount(@Field("username") String username,@Field("password") String password);

    /**
     * 5.2 注册
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST("/user/register")
    @FormUrlEncoded
    Observable<BaseResponse<User>> registerAccount(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);


    //6.收藏

    /**
     * 收藏文章列表
     * @param page 页码：拼接在链接中，从0开始。
     * @return
     */
    @GET("/lg/collect/list/{page}/json")
    Observable<BaseResponse<ArticleData>> getCollectList(@Path("page") int page);

    /**
     * 6.2 收藏站内文章
     * @param id 文章id，拼接在链接中
     * @return
     */
    @POST("/lg/collect/{id}/json")
    Observable<BaseResponse> addCollectArticle(@Path("id") int id);

    /**
     * 6.4.1 取消收藏 (文章列表)
     * @param id 拼接在链接上
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    Observable<BaseResponse> removeCollectArticle(@Path("id") int id);

    /**
     * 6.4.2 取消收藏 (我的收藏页面（该页面包含自己录入的内容）)
     * @param id 拼接在链接上
     * @param originId 列表页下发，无则为-1
     * @return
     */
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse> removeCollectArticle(@Path("id") int id,@Field("originId") int originId);


}






