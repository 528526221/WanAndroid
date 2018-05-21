package com.xulc.wanandroid.ui.article;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseActivity;
import com.xulc.wanandroid.view.MenuWindow;
import com.xulc.wanandroid.view.TitleBar;

/**
 * Date：2018/4/11
 * Desc：文章详情（网页）
 * Created by xuliangchun.
 */

public class ArticleActivity extends BaseActivity<ArticleContract.Presenter> implements MenuWindow.MenuClickListener {
    private TitleBar titleBar;
    private FrameLayout webContent;
    private AgentWeb mAgentWeb;
    private ImageView ivMenu;


    @Override
    protected void initInjector() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    protected void initView() {
        titleBar = (TitleBar) findViewById(R.id.titleBar);
        webContent = (FrameLayout) findViewById(R.id.webContent);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuWindow menuWindow = new MenuWindow(ArticleActivity.this,ArticleActivity.this);
                menuWindow.showAsDropDown(ivMenu);
            }
        });
        String title = getIntent().getStringExtra("title");
        String link = getIntent().getStringExtra("link");
        titleBar.setTitle(Html.fromHtml(title));
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(webContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()//
                .ready()
                .go(link);
        WebSettings settings = mAgentWeb.getWebCreator().getWebView().getSettings();
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLoadWithOverviewMode(true);
        //支持屏幕缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);


    }

    @Override
    public void onMenuCollect() {
        ToastUtils.showShort("暂未实现~");
    }

    @Override
    public void onMenuOpenWithBrowser() {
        Uri uri = Uri.parse(getIntent().getStringExtra("link"));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (!mAgentWeb.back()) {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
