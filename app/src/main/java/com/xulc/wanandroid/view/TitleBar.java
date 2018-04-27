package com.xulc.wanandroid.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xulc.wanandroid.R;

/**
 * Date：2018/4/17
 * Desc：利用toolbar作为title只是为了在api（19-21）的时候可以沉浸式，我觉得设置xml的背景代价太大
 * Created by xuliangchun.
 */

public class TitleBar extends LinearLayout {
    private TextView tvTitle;
    public TitleBar(Context context) {
        this(context,null);
    }


    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_title_bar,this,true);
        tvTitle = findViewById(R.id.tvTitle);
    }

    public TitleBar setTitle(CharSequence title){
        tvTitle.setText(title);
        return this;
    }
}
