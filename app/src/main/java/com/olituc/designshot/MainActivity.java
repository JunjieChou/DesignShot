package com.olituc.designshot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.olituc.designshot.Fragment.MyPlanFragment;
import com.olituc.designshot.Fragment.SearchEverythingFragment;
import com.olituc.designshot.Fragment.ShareLocationFragment;
import com.olituc.designshot.Utils.NoSlideViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    /**
     * 对应的布局对象
     */
    private NoSlideViewPager mViewPager;
    private SearchView sv_search_everything;
    private ImageView mIV_MyPlan;
    private ImageView mIV_ShareLocation;
    private ImageView mIV_redirectToWeahter;
    private ImageView mIV_redirectToUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();//初始化
    }

    /**
     * 初始化界面控件
     */
    private void initialization() {
        setContentView(R.layout.activity_main);

        //初始化控件
        initializeViews();
        //初始化事件（点击、长按等等。。。）
        initializeEvents();
        //初始化
        initializeFragments();
    }

    /**
     * 初始化控件
     */
    private void initializeViews() {
        mViewPager = findViewById(R.id.vp_main);
        sv_search_everything = findViewById(R.id.sv_search_everything);
        mIV_MyPlan = findViewById(R.id.iv_MyPlan);
        mIV_ShareLocation = findViewById(R.id.iv_ShareLocation);
        mIV_redirectToWeahter = findViewById(R.id.iv_redirectToWeather);
        mIV_redirectToUser = findViewById(R.id.iv_redirectToUser);

        mIV_redirectToUser.setImageResource(R.drawable.user);
        mIV_redirectToWeahter.setImageResource(R.drawable.marshmellowcloud);
    }


    /**
     * 初始化事件，点击事件
     */
    private void initializeEvents() {
        sv_search_everything.setOnClickListener(this);
        mIV_MyPlan.setOnClickListener(this);
        mIV_ShareLocation.setOnClickListener(this);
        mViewPager.setScroll(false);
    }

    /**
     * 初始化Fragments对象
     * 需要注意此处添加的mfragments对象的顺序。以此判对position
     */
    private void initializeFragments() {
        mFragments = new ArrayList<>();

        //注意此处的顺序
        mFragments.add(new MyPlanFragment());
        mFragments.add(new ShareLocationFragment());
        mFragments.add(new SearchEverythingFragment());

        //geiviewpager设置适配器
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
                resetImages();
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    /**
     * 根据选择的viewpager页面选择对应的tab
     *
     * @param position
     */
    private void selectTab(int position) {
        switch (position) {
            case 0:
                mIV_MyPlan.setImageResource(R.drawable.checked_myplan_module1);
                break;
            case 1:
                mIV_ShareLocation.setImageResource(R.drawable.checked_sharelocation_module1);
            default:
                break;
        }
        mViewPager.setCurrentItem(position, true);
    }

    /**
     * 初始化顶部tab的图标
     */
    private void resetImages() {
        mIV_MyPlan.setImageResource(R.drawable.unchecked_myplan_module1);
        mIV_ShareLocation.setImageResource(R.drawable.unchecked_sharelocation_module1);
    }


    /**
     * 重写接口中的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        resetImages();
        switch (v.getId()) {
            case R.id.iv_MyPlan:
                selectTab(0);
                break;
            case R.id.iv_ShareLocation:
                selectTab(1);
                break;
            case R.id.sv_search_everything:
                selectTab(2);
                break;
            default:
                break;
        }
    }


    /**
     * 天气点击事件
     *
     * @param view
     */
    public void showWeatherActivity(View view) {
        Intent intent = new Intent(this, WeatherInfoActivity.class);
        startActivity(intent);
    }

    /**
     * 用户点击事件
     * 判断是否已经登录，如果是未登录，实现跳转
     * 如果是已登录，实现注销 更新界面
     *
     * @param view
     */
    public void showUserInfoActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}