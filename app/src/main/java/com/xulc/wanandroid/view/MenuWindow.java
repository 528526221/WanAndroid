package com.xulc.wanandroid.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xulc.wanandroid.R;

/**
 * Date：2018/5/7
 * Desc：弹出菜单
 * Created by xuliangchun.
 */

public class MenuWindow extends PopupWindow implements View.OnClickListener {
    private MenuClickListener listener;
    public MenuWindow(Context context,MenuClickListener listener) {
        super(context);
        this.listener = listener;
        View root = LayoutInflater.from(context).inflate(R.layout.layout_window_menu,null);
        TextView tvCollect = root.findViewById(R.id.tvCollect);
        TextView tvOpenWithBrowser = root.findViewById(R.id.tvOpenWithBrowser);
        tvCollect.setOnClickListener(this);
        tvOpenWithBrowser.setOnClickListener(this);
        this.setContentView(root);
        this.setAnimationStyle(R.style.menu_window_style);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvCollect:
                if (listener != null){
                    listener.onMenuCollect();
                    dismiss();
                }
                break;
            case R.id.tvOpenWithBrowser:
                if (listener != null){
                    listener.onMenuOpenWithBrowser();
                    dismiss();
                }
                break;
        }
    }

    public interface MenuClickListener{
        void onMenuCollect();
        void onMenuOpenWithBrowser();
    }
}
