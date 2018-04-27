package com.xulc.wanandroid.ui.knowledgesystem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xulc.wanandroid.bean.KnowledgeSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/4/13
 * Desc：
 * Created by xuliangchun.
 */

public class SecondDecoration extends RecyclerView.ItemDecoration {
    private List<KnowledgeSystem> systemList;
    private Paint mPaint;
    private int titleHeight;

    public SecondDecoration(int titleHeight) {
        this.titleHeight = titleHeight;
        systemList = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(40);
    }

    public void setSystemList(List<KnowledgeSystem> systemList) {
        this.systemList = systemList;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        if (position == 0){
            outRect.top = titleHeight;
        }else {
            if (systemList.get(position).getParentChapterId() != systemList.get(position-1).getParentChapterId()){
                outRect.top = titleHeight;
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i=0;i<parent.getChildCount();i++){

            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (position == 0 || systemList.get(position).getParentChapterId() != systemList.get(position-1).getParentChapterId()){
                mPaint.setColor(Color.parseColor("#FF4E00"));
                RectF targetRectf = new RectF(parent.getPaddingLeft(),child.getTop()-titleHeight,parent.getWidth()-parent.getPaddingRight(),child.getTop());
                c.drawRect(targetRectf,mPaint);

                float baseline = (targetRectf.bottom + targetRectf.top - mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top) / 2;
                mPaint.setColor(Color.WHITE);

                c.drawText(systemList.get(position).getParentChapterName(),targetRectf.centerX()-mPaint.measureText(systemList.get(position).getParentChapterName())/2,baseline,mPaint);
            }


        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int firstItem = ((LinearLayoutManager)parent.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstItem+1<systemList.size()){
            if (systemList.get(firstItem).getParentChapterId() != systemList.get(firstItem+1).getParentChapterId()){
                //下一个是其他父类时，吸顶要往上顶
                int distance = parent.getChildAt(0).getBottom();

                int left = parent.getPaddingLeft();
                int top = Math.min(0,parent.getPaddingTop()+distance-titleHeight);
                int right = parent.getWidth()-parent.getPaddingRight();
                int bottom = Math.min(titleHeight,parent.getPaddingTop()+distance);
                mPaint.setColor(Color.parseColor("#FF4E00"));

                RectF targetRectf = new RectF(left,top,right,bottom);
                c.drawRect(targetRectf,mPaint);


                float baseline = (targetRectf.bottom + targetRectf.top - mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top) / 2;
                mPaint.setColor(Color.WHITE);

                c.drawText(systemList.get(firstItem).getParentChapterName(),targetRectf.centerX()-mPaint.measureText(systemList.get(firstItem).getParentChapterName())/2,baseline,mPaint);
            }else {
                mPaint.setColor(Color.parseColor("#FF4E00"));

                RectF targetRectf = new RectF(parent.getPaddingLeft(),parent.getPaddingTop(),parent.getWidth()-parent.getPaddingRight(),parent.getPaddingTop()+titleHeight);
                c.drawRect(targetRectf,mPaint);


                float baseline = (targetRectf.bottom + targetRectf.top - mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top) / 2;
                mPaint.setColor(Color.WHITE);

                c.drawText(systemList.get(firstItem).getParentChapterName(),targetRectf.centerX()-mPaint.measureText(systemList.get(firstItem).getParentChapterName())/2,baseline,mPaint);
            }
        }




    }


}
