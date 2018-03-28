package com.olituc.designshot.Fragment;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.olituc.designshot.R;
import com.olituc.designshot.Utils.CustomScrollView;
import com.olituc.designshot.Utils.HorizontalListView;

/**
 * Created by olituc on 3/13/18.
 * All Rights Reserved by olituc
 */

public class ShareHotLocationFragment extends Fragment implements View.OnClickListener {

    /**
     * 全局变量
     */
    private HorizontalListView hlv_top_location;
    private ListView lv_all_hot_location;
    private CustomScrollView mScrollView_shareLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sharelocation_hotlocation,container,false);
        initializeViews(view);
        inializeAdapter();
        return view;
    }

    /**
     * 初始化控件的适配器
     * 在线程中操作，比较耗时
     */
    private void inializeAdapter() {
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                hlv_top_location.setAdapter(new AllHotLocationAdapter());
                lv_all_hot_location.setAdapter(new HorizontalLVAdapter());
                Looper.loop();
            }
        }.start();
    }

    /**
     * 找到控件
     * @param view
     */
    private void initializeViews(View view) {
        hlv_top_location = view.findViewById(R.id.hlv_top_location);
        lv_all_hot_location = view.findViewById(R.id.lv_all_hot_location);
        mScrollView_shareLocation = view.findViewById(R.id.ScrollView_shareLocation);

        /**
         * 设置焦点
         */
        hlv_top_location.requestFocus();
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
    }

    /**
     * 所有热点适配器
     */
    private class AllHotLocationAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    /**
     * 顶端热点适配器
     */
    private class HorizontalLVAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
