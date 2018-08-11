package com.icall.free.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectAll;
import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.icall.free.R;
import com.icall.free.activity.GoogleSearchActivity;
import com.icall.free.views.ICallViewPager;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends BaseFragment  {
    private static final int MAX_TAB_COUNT = 3;

    private List<Fragment> mTabList;
    private HotFragment mHotFragment;
    private FunnyFragment mFunnyFragment;
    private GirlsFragment mGirlsFragment;

    private MyViewPagerAdapter mMyViewPagerAdapter;

    private static final int HOT_POSITION = 0; // 拨号
    private static final int FUNNY_POSITION = 1; // 流量
    private static final int GIRLS_POSITION = 2; // 流量

    private int currentIndex = HOT_POSITION;

    @InjectAll
    Views views;

    class Views{
        @InjectBinder(method = "click", listeners = { OnClick.class })
        public RelativeLayout discover_hot_rl;

        @InjectBinder(method = "click", listeners = { OnClick.class })
        public RelativeLayout discover_funny_rl;

        @InjectBinder(method = "click", listeners = { OnClick.class })
        public RelativeLayout discover_girls_rl;

        @InjectBinder(method = "click", listeners = { OnClick.class })
        public View google_search_view;

        public TextView discover_hot_tv;
        public View discover_hot_line;

        public TextView discover_funny_tv;
        public View discover_funny_line;

        public TextView discover_girls_tv;
        public View discover_girls_line;

        public ICallViewPager discover_viewpager;

        public EditText google_search_et;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.discover_activity, container, false);
        Handler_Inject.injectOrther(this, rootView);
        return rootView;
    }

    @InjectInit
    private void init() {
        addTabs();
        views.discover_viewpager.setOffscreenPageLimit(MAX_TAB_COUNT);
        mMyViewPagerAdapter = new MyViewPagerAdapter(this.getFragmentManager());
        views.discover_viewpager.setAdapter(mMyViewPagerAdapter);
        views.google_search_et.setFocusable(false);
//        EventBus.getDefault().register(this);
    }

    // 添加标签页 Fragment
    private void addTabs() {
        mTabList = new ArrayList<Fragment>();

        mHotFragment = new HotFragment();
        mFunnyFragment = new FunnyFragment();
        mGirlsFragment = new GirlsFragment();

        mTabList.add(mHotFragment);
        mTabList.add(mFunnyFragment);
        mTabList.add(mGirlsFragment);
    }

    @Override
    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return mTabList.get(arg0);
        }

        @Override
        public int getCount() {
            return mTabList.size();
        }
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.discover_hot_rl:
                setCurrentTab(HOT_POSITION);
                break;
            case R.id.discover_funny_rl:
                setCurrentTab(FUNNY_POSITION);
                break;
            case R.id.discover_girls_rl:
                //获取更多积分
                setCurrentTab(GIRLS_POSITION);
//                Toast.makeText(getContext(), "get more", Toast.LENGTH_SHORT).show();
                break;
            case R.id.google_search_view:
                startActivity(GoogleSearchActivity.class);
                break;
        }
    }

    // 设置标签
    private void setCurrentTab(int operate) {
        boolean needChange = false;
        switch (operate) {
            case HOT_POSITION:
                if (operate != currentIndex) { // 取消先前选中的tag
                    setBottomTabView(HOT_POSITION);
                    needChange = true;
                }
                break;
            case FUNNY_POSITION:
                if (operate != currentIndex) {
                    needChange = true;
                    setBottomTabView(FUNNY_POSITION);
                }
                break;
            case GIRLS_POSITION:
                if (operate != currentIndex) {
                    needChange = true;
                    setBottomTabView(GIRLS_POSITION);
                }
                break;
        }
        if (needChange) {
            currentIndex = operate;
            views.discover_viewpager.setCurrentItem(currentIndex, false);
        }
    }

    private void setBottomTabView(int index) {
        int norId = R.color.calls_tab_nor_color;
        int selId = R.color.calls_tab_sel_color;
        switch (index) {
            case HOT_POSITION:
                views.discover_hot_tv.setTextColor(getResources().getColor(selId));
                views.discover_hot_line.setBackgroundResource(selId);
                views.discover_hot_line.setVisibility(View.VISIBLE);

                views.discover_funny_tv.setTextColor(getResources().getColor(norId));
                views.discover_funny_line.setBackgroundResource(norId);
                views.discover_funny_line.setVisibility(View.INVISIBLE);

                views.discover_girls_tv.setTextColor(getResources().getColor(norId));
                views.discover_girls_line.setBackgroundResource(norId);
                views.discover_girls_line.setVisibility(View.INVISIBLE);

                break;
            case FUNNY_POSITION:
                views.discover_hot_tv.setTextColor(getResources().getColor(norId));
                views.discover_hot_line.setBackgroundResource(norId);
                views.discover_hot_line.setVisibility(View.INVISIBLE);

                views.discover_funny_tv.setTextColor(getResources().getColor(selId));
                views.discover_funny_line.setBackgroundResource(selId);
                views.discover_funny_line.setVisibility(View.VISIBLE);

                views.discover_girls_tv.setTextColor(getResources().getColor(norId));
                views.discover_girls_line.setBackgroundResource(norId);
                views.discover_girls_line.setVisibility(View.INVISIBLE);
                break;
            case GIRLS_POSITION:
                views.discover_hot_tv.setTextColor(getResources().getColor(norId));
                views.discover_hot_line.setBackgroundResource(norId);
                views.discover_hot_line.setVisibility(View.INVISIBLE);

                views.discover_funny_tv.setTextColor(getResources().getColor(norId));
                views.discover_funny_line.setBackgroundResource(norId);
                views.discover_funny_line.setVisibility(View.INVISIBLE);

                views.discover_girls_tv.setTextColor(getResources().getColor(selId));
                views.discover_girls_line.setBackgroundResource(selId);
                views.discover_girls_line.setVisibility(View.VISIBLE);
                break;
        }

    }

//    public void onEventMainThread(Message msg) {
//        switch (MessageType.values()[msg.what]) {
//            case SCROOL_HIDE_DIAL_MESSAGE:
//                views.diapad_layout_main_ll.setVisibility(View.GONE);
//                break;
//            case HIDE_TAB_MSG:
//                break;
//            case SHOW_TAB_MSG:
//                break;
//            case SHOW_TAB_ME_NEW:
//                break;
//            case DIAL_INPUT_NULL:
//                break;
//            case DIAL_INPUT_TEXT:
//                break;
//            default:
//                break;
//        }
//    }
}
