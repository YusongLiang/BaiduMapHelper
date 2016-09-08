package com.github.yusongliang.baidumaphelper.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.yusongliang.baidumaphelper.R;

public class MainActivity extends BaseActivity {

    private RecyclerView mRvFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
        initListener();
    }

    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvFunction.setLayoutManager(manager);

    }

    private void initView() {
        mRvFunction = (RecyclerView) findViewById(R.id.rv_function);
    }

    private void initListener() {

    }
}
