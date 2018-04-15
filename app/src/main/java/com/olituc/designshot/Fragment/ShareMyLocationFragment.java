package com.olituc.designshot.Fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.olituc.designshot.R;
import com.olituc.designshot.ShowLocationActivity;
import com.olituc.designshot.db.dao.SpotInfoDao;
import com.olituc.designshot.domain.MySpotInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olituc on 3/13/18.
 * All Rights Reserved by olituc
 */

public class ShareMyLocationFragment extends Fragment implements View.OnClickListener {

    private static final int QUERY_SUCCESS = 1;
    private static final int SHOW_SPOT = 1;
    private GridView mGv_myLocation;
    private SpotInfoDao mSpotInfoDao;
    private List<MySpotInfo> mMySpotInfos = new ArrayList<>();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case QUERY_SUCCESS:
                    mMySpotInfos = (List<MySpotInfo>) msg.obj;
                    mGv_myLocation.setAdapter(new MyLocationGridViewAdapter());
                    break;

                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sharelocation_mylocation, container, false);
        initialization(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeGridView();
        initializeAdapter();
    }

    private void initializeGridView() {

    }

    /**
     * 适配我的计划gridview
     */
    private void initializeAdapter() {
        mGv_myLocation = getActivity().findViewById(R.id.gv_MyLocation);
        mSpotInfoDao = new SpotInfoDao(getContext());
        querySpotInfo();
    }

    /**
     * 查找信息
     */
    public void querySpotInfo() {
        new Thread() {
            @Override
            public void run() {
                List<MySpotInfo> query = mSpotInfoDao.query();
                Message msg = Message.obtain();
                msg.what=QUERY_SUCCESS;
                msg.obj = query;
                mHandler.sendMessage(msg);
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
                Bundle bundle = new Bundle();
                bundle.putString("spotLocation",mMySpotInfos.get(position).getSpotLocation());
                bundle.putString("spotRemark",mMySpotInfos.get(position).getSpotRemark());
                bundle.putByteArray("spotPic",mMySpotInfos.get(position).getSpotPic());
                bundle.putInt("spotId",mMySpotInfos.get(position).getSpotId());
                startActivityForResult(new Intent(getActivity(), ShowLocationActivity.class).putExtras(bundle),SHOW_SPOT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SHOW_SPOT:
                querySpotInfo();
                break;

            default:
                break;
        }
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
            return mMySpotInfos.size();
        }

        @Override
        public MySpotInfo getItem(int position) {
            return mMySpotInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getContext(), R.layout.gv_location_conponent, null);
            TextView tv_location_spot = view.findViewById(R.id.tv_location_spot);
            LinearLayout ll_gv_child_layout = view.findViewById(R.id.ll_gv_child_layout);
            tv_location_spot.setText(mMySpotInfos.get(position).getSpotLocation().toString());
            ll_gv_child_layout.setBackgroundDrawable(new BitmapDrawable(mMySpotInfos.get(position).getSpotPicBitmap()));
            return view;
        }
    }
}
