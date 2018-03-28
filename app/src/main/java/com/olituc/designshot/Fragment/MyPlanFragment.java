package com.olituc.designshot.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.Toast;

import com.olituc.designshot.CreateNewPlanActivity;
import com.olituc.designshot.R;
import com.olituc.designshot.SettingsActivity;
import com.olituc.designshot.db.dao.PlanInfoDao;
import com.olituc.designshot.domain.MyPlanInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olituc on 3/12/18.
 * All Rights Reserved by olituc
 */

public class MyPlanFragment extends Fragment implements View.OnClickListener {

    private GridView gv_myPlan;
    private MyPlanInfo mMyPlanInfo;
    private List<MyPlanInfo> mMyPlanInfos;
    private LinearLayout ll_sync_cloud_myPlan;
    private PlanInfoDao mPlanInfoDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myplan, container, false);
        initializeViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //测试
        //test();
        //初始化gridview
        initializeGridView();
        initializeEvents();
        super.onActivityCreated(savedInstanceState);
    }
    /**
     * 初始化事件
     */
    private void initializeEvents() {
        ll_sync_cloud_myPlan.setOnClickListener(this);
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initializeViews(View view) {
        mMyPlanInfos = new ArrayList<>();
        gv_myPlan = getActivity().findViewById(R.id.gv_MyPlan);
        ll_sync_cloud_myPlan = view.findViewById(R.id.ll_sync_cloud_myPlan);
    }

    /**
     * 测试MyPlanInfoGridView
     */
    private void test() {
        for (int i = 0; i < 14; ++i) {
            mMyPlanInfo = new MyPlanInfo("2018.09.08", "苏州" + i, "人像");
            mMyPlanInfos.add(mMyPlanInfo);
        }
    }

    /**
     * 初始化initializeGridView
     */
    private void initializeGridView() {
        gv_myPlan = getActivity().findViewById(R.id.gv_MyPlan);
        mPlanInfoDao = new PlanInfoDao(getContext());
        new Thread(){
            @Override
            public void run() {
                gv_myPlan.setAdapter(new MyPlanAdapter());
            }
        }.start();
        gv_myPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), position + "isclicked", Toast.LENGTH_SHORT).show();
                if(position == mMyPlanInfos.size()){
                    startActivity(new Intent(getContext(), CreateNewPlanActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 同步云计划时间
             * 进行是否登录的判断
             * 从服务器下载数据以及提交数据给服务器
             */
            case R.id.ll_sync_cloud_myPlan:
                startActivity(new Intent(getContext(), SettingsActivity.class));
                Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**
     * GridView的适配器
     */
    private class MyPlanAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getContext(), R.layout.gv_child_conponent, null);
            TextView tv_plan_time = view.findViewById(R.id.tv_plan_time);
            TextView tv_plan_spot = view.findViewById(R.id.tv_plan_spot);
            TextView tv_plan_theme = view.findViewById(R.id.tv_plan_theme);
            if(position < mMyPlanInfos.size()){
                mMyPlanInfo = getItem(position);
                tv_plan_time.setText("时间：" + mMyPlanInfo.getPlanTime());
                tv_plan_spot.setText("地点：" + mMyPlanInfo.getPlanCity());
                tv_plan_theme.setText("主题：" + mMyPlanInfo.getPlanTheme());
            }else{
                tv_plan_time.setText("");
                tv_plan_spot.setText("＋");
                tv_plan_spot.setTextSize(40);
                tv_plan_spot.setTextColor(Color.BLACK);
                tv_plan_theme.setText("");
                view.setBackgroundColor(Color.WHITE);
            }
            return view;
        }

        @Override
        public int getCount() {
            return mMyPlanInfos.size()+1;
        }

        @Override
        public MyPlanInfo getItem(int position) {
            return mMyPlanInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
