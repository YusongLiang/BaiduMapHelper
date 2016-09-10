package com.github.yusongliang.baidumaphelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.yusongliang.baidumaphelper.R;
import com.github.yusongliang.baidumaphelper.activity.MapActivity;
import com.github.yusongliang.baidumaphelper.activity.MapFragmentActivity;
import com.github.yusongliang.baidumaphelper.activity.PagerMapFragmentActivity;

/**
 * 功能列表Adapter
 */
public class FunctionListAdapter extends RecyclerView.Adapter<FunctionListAdapter.ViewHolder> {

    private final Context mContext;
    private final String[] mNames = {"BaseMapActivity演示", "BaseMapFragment演示-Viewpager","BaseMapFragment演示-一般"};

    public FunctionListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public FunctionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_function, parent, false));
    }

    @Override
    public void onBindViewHolder(final FunctionListAdapter.ViewHolder holder, int position) {
        holder.mTvIndex.setText(String.valueOf(position + 1));
        holder.mTvName.setText(mNames[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class c = null;
                switch (holder.getAdapterPosition()) {
                    case 0:
                        c = MapActivity.class;
                        break;
                    case 1:
                        c = PagerMapFragmentActivity.class;
                        break;
                    case 2:
                        c= MapFragmentActivity.class;
                        break;
                }
                mContext.startActivity(new Intent(mContext, c));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvIndex;
        private final TextView mTvName;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvIndex = (TextView) itemView.findViewById(R.id.tv_item_index);
            mTvName = (TextView) itemView.findViewById(R.id.tv_item_name);
        }
    }
}
