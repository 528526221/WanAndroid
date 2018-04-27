package com.xulc.wanandroid.ui.my;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class MyDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private int a = 100;
    private int b = 5;

    public MyDecoration() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = b;

        outRect.right = a;

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i=0;i<parent.getChildCount();i++){
            View view = parent.getChildAt(i);
            mPaint.setColor(Color.parseColor("#999999"));

            c.drawLine(parent.getWidth()-parent.getPaddingRight()-a*1/2,view.getTop()+view.getHeight()/3,parent.getWidth()-parent.getPaddingRight()-a*1/4,view.getTop()+view.getHeight()/2,mPaint);
            c.drawLine(parent.getWidth()-parent.getPaddingRight()-a*1/2,view.getBottom()-view.getHeight()/3,parent.getWidth()-parent.getPaddingRight()-a*1/4,view.getTop()+view.getHeight()/2,mPaint);

            mPaint.setColor(Color.parseColor("#F3F5F9"));
            c.drawRect(parent.getPaddingLeft(),view.getBottom(),parent.getWidth()-parent.getPaddingRight(),view.getBottom()+b,mPaint);
        }

    }
}
