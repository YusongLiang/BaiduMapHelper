package com.github.yusongliang.baidumaphelper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.baidumaphelper.adapter.FunctionListAdapter;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 功能列表
     */
    private RecyclerView mRvFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
    }

    private void initView() {
        mRvFunction = (RecyclerView) findViewById(R.id.rv_function);
    }

    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvFunction.setLayoutManager(manager);
        FunctionListAdapter adapter = new FunctionListAdapter(this);
        mRvFunction.setAdapter(adapter);
    }

}
