package com.xulc.wanandroid.ui.index;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Date：2018/4/11
 * Desc：
 * Created by xuliangchun.
 */

public class IndexDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private int dividerHeight = 5;

    public IndexDecoration() {
        this.mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#f3f5f9"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getLayoutManager() instanceof LinearLayoutManager){
            if (parent.getChildAdapterPosition(view) != 0){
                outRect.top = dividerHeight;
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getLayoutManager() instanceof LinearLayoutManager){
            for (int i=0;i<parent.getChildCount();i++){
                View view = parent.getChildAt(i);
                c.drawRect(parent.getPaddingLeft(),view.getTop()-dividerHeight,parent.getWidth()-parent.getPaddingRight(),view.getTop(),mPaint);
            }
        }
    }
}
