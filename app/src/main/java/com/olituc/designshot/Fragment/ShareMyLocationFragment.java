package com.olituc.designshot.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.olituc.designshot.R;

/**
 * Created by olituc on 3/13/18.
 * All Rights Reserved by olituc
 */

public class ShareMyLocationFragment extends Fragment implements View.OnClickListener {

    private GridView mGv_myLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sharelocation_mylocation, container, false);
        initialization(view);
        initializeAdapter();
        return view;
    }

    /**
     * 适配我的计划gridview
     */
    private void initializeAdapter() {
        new Thread() {
            @Override
            public void run() {
                mGv_myLocation.setAdapter(new MyLocationGridViewAdapter());
            }
        }.start();
    }

    /**
     * 获取到控件
     *
     * @param view
     */
    private void initialization(View view) {
        mGv_myLocation = view.findViewById(R.id.gv_MyLocation);
        mGv_myLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),position+"isclicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 设置我的计划适配器
     */
    private class MyLocationGridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 10;
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
            View view = View.inflate(getContext(), R.layout.gv_location_conponent, null);
            TextView tv_location_spot = (TextView) view.findViewById(R.id.tv_location_spot);
            tv_location_spot.setText("sadfrthdfnlkjdnxks;oerkg 'dkngkljxn;k离婚卡技术的划分了空间的划分给啥地方叫过来送客人过来看世界的风格水电费个");
            return view;
        }
    }
}
