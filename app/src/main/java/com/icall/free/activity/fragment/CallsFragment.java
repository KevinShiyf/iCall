package com.icall.free.activity.fragment;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.event.EventBus;
import com.android.pc.ioc.inject.InjectAll;
import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.icall.free.R;
import com.icall.free.activity.CountryCodeActivity;
import com.icall.free.activity.MainTabActivity;
import com.icall.free.event.MessageType;
import com.icall.free.views.ICallViewPager;

import java.util.ArrayList;
import java.util.List;

public class CallsFragment extends BaseFragment {
    @InjectAll
    Views views;

    class Views{
        @InjectBinder(method = "click", listeners = { OnClick.class })
        public TextView calls_more_tv;
        @InjectBinder(method = "click", listeners = { OnClick.class })
        public RelativeLayout calls_records_rl, calls_contacts_rl;
        @InjectBinder(method = "click", listeners = { OnClick.class })
        public FrameLayout dialpad_code_fl;
        @InjectBinder(method = "click", listeners = { OnClick.class })
        public RelativeLayout dialpad_one_rl, dialpad_two_rl, dialpad_three_rl, dialpad_four_rl, dialpad_five_rl, dialpad_six_rl;
        @InjectBinder(method = "click", listeners = { OnClick.class })
        public RelativeLayout dialpad_seven_rl, dialpad_eight_rl, dialpad_nine_rl, dialpad_star_rl, dialpad_zero_rl, dialpad_well_rl;
        @InjectBinder(method = "click", listeners = { OnClick.class })
        public RelativeLayout dialpad_clear_rl, dialpad_call_rl, dialpad_del_rl;
        public TextView dialpad_phone_tv;
        public ICallViewPager calls_viewpager;
        public TextView calls_recents_tv;
        public View calls_recents_line;

        public TextView calls_contacts_tv;
        public View calls_contacts_line;

        private LinearLayout diapad_layout_main_ll;


    }
    private final static int SELECT_CODE_CODE = 101;
    private static final int MAX_TAB_COUNT = 3;

    private List<Fragment> mTabList;
    private RecentsFragment mRecentsFragment;
    private ContactsFragment mContactsFragment;

    private MyViewPagerAdapter mMyViewPagerAdapter;
    private static final int RECENTS_POSITION = 0; // 拨号
    private static final int CONTACTS_POSITION = 1; // 流量

    private int currentIndex = RECENTS_POSITION;

    private StringBuffer inputPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.calls_activity, container, false);
        Handler_Inject.injectOrther(this, rootView);
        return rootView;

    }

    @InjectInit
    private void init() {
        inputPhone = new StringBuffer("");
        addTabs();
        views.calls_viewpager.setOffscreenPageLimit(MAX_TAB_COUNT);
        mMyViewPagerAdapter = new MyViewPagerAdapter(this.getFragmentManager());
        views.calls_viewpager.setAdapter(mMyViewPagerAdapter);
        EventBus.getDefault().register(this);
    }

    // 添加标签页 Fragment
    private void addTabs() {
        mTabList = new ArrayList<Fragment>();

        mRecentsFragment = new RecentsFragment();
        mContactsFragment = new ContactsFragment();

        mTabList.add(mRecentsFragment);
        mTabList.add(mContactsFragment);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
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
            case R.id.calls_records_rl:
                setCurrentTab(RECENTS_POSITION);
                break;
            case R.id.calls_contacts_rl:
                setCurrentTab(CONTACTS_POSITION);
                break;
            case R.id.calls_more_tv:
               //获取更多积分
                Toast.makeText(getContext(), "get more", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dialpad_one_rl:
                dialPadInput("1");
                break;
            case R.id.dialpad_two_rl:
                dialPadInput("2");
                break;
            case R.id.dialpad_three_rl:
                dialPadInput("3");
                break;
            case R.id.dialpad_four_rl:
                dialPadInput("4");
                break;
            case R.id.dialpad_five_rl:
                dialPadInput("5");
                break;
            case R.id.dialpad_six_rl:
                dialPadInput("6");
                break;
            case R.id.dialpad_seven_rl:
                dialPadInput("7");
                break;
            case R.id.dialpad_eight_rl:
                dialPadInput("8");
                break;
            case R.id.dialpad_nine_rl:
                dialPadInput("9");
                break;
            case R.id.dialpad_star_rl:
                dialPadInput("*");
                break;
            case R.id.dialpad_zero_rl:
                dialPadInput("0");
                break;
            case R.id.dialpad_well_rl:
                dialPadInput("#");
                break;
            case R.id.dialpad_clear_rl:
                dialPadInput("CLEAR");
                break;
            case R.id.dialpad_del_rl:
                dialPadInput("DEL");
                break;
            case R.id.dialpad_call_rl:

                break;
            case R.id.dialpad_code_fl:
                startActivity(CountryCodeActivity.class, SELECT_CODE_CODE);
                break;
        }
    }

    private void dialPadInput(String text) {
        if ("DEL".equals(text)) {
            if (inputPhone.length() > 0) {
                inputPhone.delete(inputPhone.length() - 1, inputPhone.length());
            }
        } else if ("CLEAR".equals(text)) {
            if (inputPhone.length() > 0) {
                inputPhone.delete(0, inputPhone.length());
            }
        } else {
            inputPhone.append(text);
        }
        views.dialpad_phone_tv.setText(inputPhone.toString());
    }

    // 设置标签
    private void setCurrentTab(int operate) {
        boolean needChange = false;
        switch (operate) {
            case RECENTS_POSITION:
                if (operate != currentIndex) { // 取消先前选中的tag
                    setBottomTabView(RECENTS_POSITION);
                    needChange = true;
                }
                break;
            case CONTACTS_POSITION:
                if (operate != currentIndex) {
                    needChange = true;
                    setBottomTabView(CONTACTS_POSITION);
                }
                break;
        }
        if (needChange) {
            currentIndex = operate;
            views.calls_viewpager.setCurrentItem(currentIndex, false);
        }
    }

    private void setBottomTabView(int index) {
        switch (index) {
            case RECENTS_POSITION:
                views.calls_recents_tv.setTextColor(getResources().getColor(R.color.calls_tab_sel_color));
                views.calls_recents_line.setBackgroundResource(R.color.calls_tab_nor_color);
                views.calls_recents_line.setVisibility(View.VISIBLE);

                views.calls_contacts_tv.setTextColor(getResources().getColor(R.color.calls_tab_nor_color));
                views.calls_contacts_line.setBackgroundResource(R.color.calls_tab_nor_color);
                views.calls_contacts_line.setVisibility(View.INVISIBLE);

                views.diapad_layout_main_ll.setVisibility(View.VISIBLE);
                break;
            case CONTACTS_POSITION:
                views.calls_recents_tv.setTextColor(getResources().getColor(R.color.calls_tab_nor_color));
                views.calls_recents_line.setBackgroundResource(R.color.calls_tab_nor_color);
                views.calls_recents_line.setVisibility(View.INVISIBLE);

                views.calls_contacts_tv.setTextColor(getResources().getColor(R.color.calls_tab_sel_color));
                views.calls_contacts_line.setBackgroundResource(R.color.calls_tab_nor_color);
                views.calls_contacts_line.setVisibility(View.VISIBLE);

                views.diapad_layout_main_ll.setVisibility(View.GONE);
                break;
        }

    }

    public void onEventMainThread(Message msg) {
        switch (MessageType.values()[msg.what]) {
            case SCROOL_HIDE_DIAL_MESSAGE:
                views.diapad_layout_main_ll.setVisibility(View.GONE);
                break;
            case HIDE_TAB_MSG:
                break;
            case SHOW_TAB_MSG:
                break;
            case SHOW_TAB_ME_NEW:
                break;
            case DIAL_INPUT_NULL:
                break;
            case DIAL_INPUT_TEXT:
                break;
            default:
                break;
        }
    }
}
