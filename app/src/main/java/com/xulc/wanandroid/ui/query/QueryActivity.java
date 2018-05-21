package com.xulc.wanandroid.ui.query;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.xulc.wanandroid.R;
import com.xulc.wanandroid.base.BaseActivity;
import com.xulc.wanandroid.bean.HotKey;
import com.xulc.wanandroid.ui.queryresult.QueryResultActivity;
import com.xulc.wanandroid.view.IrregularLayout;

import java.util.List;

/**
 * Date：2018/5/21
 * Desc：
 * Created by xuliangchun.
 */

public class QueryActivity extends BaseActivity<QueryPresenter> implements QueryContract.View{
    private EditText etQuery;
    private IrregularLayout irregularLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_query;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        irregularLayout = (IrregularLayout) findViewById(R.id.irregularLayout);
        etQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    queryKey(etQuery.getText().toString());
                    return true;
                }
                return false;
            }
        });
        mPresenter.loadHotKey();
    }

    @Override
    public void setHotKey(final List<HotKey> hotKeys) {
        String[] colors = getResources().getStringArray(R.array.shape_color_array);
        for (int i=0;i<hotKeys.size();i++){
            TextView textView = new TextView(this);
            textView.setText(hotKeys.get(i).getName());
            textView.setBackgroundResource(R.drawable.query_text_selector);
            GradientDrawable drawable = (GradientDrawable) textView.getBackground();
            drawable.setColor(Color.parseColor(colors[i%5]));
            textView.setTextColor(Color.WHITE);
            textView.setTag(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    queryKey(hotKeys.get((Integer) v.getTag()).getName());
                }
            });
            irregularLayout.addView(textView);
        }
    }

    private void queryKey(String key) {
        if (TextUtils.isEmpty(key))
            return;
        Intent intent = new Intent(this, QueryResultActivity.class);
        intent.putExtra("key",key);
        startActivity(intent);
    }
}
