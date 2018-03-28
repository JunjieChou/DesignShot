package com.olituc.designshot.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.olituc.designshot.CreateNewLocationActivity;
import com.olituc.designshot.R;
import com.olituc.designshot.Utils.NoSlideViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olituc on 3/12/18.
 * All Rights Reserved by olituc
 */

public class ShareLocationFragment extends Fragment implements View.OnClickListener {

    /**
     * 定义全局变量
     */
    private NoSlideViewPager mVP_share_location_all;
    private TextView tv_switch_to_hot_location;
    private TextView tv_switch_to_my_location;
    private TextView add_my_location;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sharelocation, container, false);
        initialization(view);
        return view;
    }

    /**
     * 整体初始化
     *
     * @param view
     */
    private void initialization(View view) {
        initializeViews(view);
        initializeEvents();
        initializeFragments();
    }

    /**
     * 初始化子Fragment
     */
    private void initializeFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new ShareHotLocationFragment());
        mFragments.add(new ShareMyLocationFragment());

        mFragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mVP_share_location_all.setAdapter(mFragmentPagerAdapter);
        mVP_share_location_all.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化事件，点击...
     */
    private void initializeEvents() {
        tv_switch_to_hot_location.setOnClickListener(this);
        tv_switch_to_my_location.setOnClickListener(this);
        add_my_location.setOnClickListener(this);
    }

    /**
     * 找到控件
     *
     * @param view
     */
    private void initializeViews(View view) {
        mVP_share_location_all = view.findViewById(R.id.vp_share_location_all);
        tv_switch_to_hot_location = view.findViewById(R.id.switch_to_hot_location);
        tv_switch_to_my_location = view.findViewById(R.id.switch_to_my_location);
        add_my_location = view.findViewById(R.id.add_my_location);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_to_hot_location:
                selectTab(0);
                Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                break;
            case R.id.switch_to_my_location:
                Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                selectTab(1);
                break;
            case R.id.add_my_location:
                Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), CreateNewLocationActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 选择当前viewpager页
     *
     * @param position
     */
    private void selectTab(int position) {
        mVP_share_location_all.setCurrentItem(position, true);
    }
}

