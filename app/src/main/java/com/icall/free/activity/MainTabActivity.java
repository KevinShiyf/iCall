package com.icall.free.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pc.ioc.event.EventBus;
import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.icall.free.R;
import com.icall.free.activity.fragment.CallsFragment;
import com.icall.free.activity.fragment.DiscoverFragment;
import com.icall.free.activity.fragment.MeFragment;
import com.icall.free.event.MessageType;
import com.icall.free.views.ICallViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wanghao 2015-4-6 主标签
 * 
 */
@InjectLayer(R.layout.main_tab_layout)
public class MainTabActivity extends BaseActivity {
	private static final int MAX_TAB_COUNT = 3;

	private List<Fragment> mTabList;
	private CallsFragment mCallsFragment;
	private DiscoverFragment mDiscoverFragment;
	private MeFragment mMeFragment;

	@InjectView
	private ICallViewPager main_tab_viewpager;

    @InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
    private LinearLayout main_tab_calls_ll;
    @InjectView
    private ImageView main_tab_calls_iv;
    @InjectView
    private TextView main_tab_calls_tv;

	@InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
	private LinearLayout main_tab_discover_ll;
	@InjectView
	private ImageView main_tab_discover_iv;
	@InjectView
	private TextView main_tab_discover_tv;


	@InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
	private LinearLayout main_tab_me_ll;
	@InjectView
	private ImageView main_tab_me_iv;
	@InjectView
	private TextView main_tab_me_tv;

	/** 底部Tab */
//	@InjectView
//	private View main_tab_bottom_ll;


	private MyViewPagerAdapter mMyViewPagerAdapter;

	private static final int CALLS_POSITION = 0; // 拨号
	private static final int DISCOVER_POSITION = 1; // 流量
	private static final int ME_POSITION = 2; // 我

	private int currentIndex = CALLS_POSITION; // 当前选中标签

	@InjectInit
	protected void init() {
        closeOtherActivity();
		addTabs();
//		measureView(main_tab_bottom_ll);

		main_tab_viewpager.setOffscreenPageLimit(MAX_TAB_COUNT);
		mMyViewPagerAdapter = new MyViewPagerAdapter(this.getSupportFragmentManager());
		main_tab_viewpager.setAdapter(mMyViewPagerAdapter);
		main_tab_viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	private void closeOtherActivity() {
		Message msg = Message.obtain();
		msg.what = MessageType.CLOSE_LOGIN_REGISTER_ACTIVITY_MSG.ordinal();
		EventBus.getDefault().post(msg);
	}

	private Handler mHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (isFinishing()) {
				return;
			}
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		showMeTagNew();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandle.removeCallbacksAndMessages(null);
	}

//	@Override
//	public void onBackPressed() {
//		if (mDialFragment.getTitleStatus() == DialFragment.DELETE_STATUS) {
//			mDialFragment.changeMainBottomTab(MessageType.SHOW_TAB_MSG.ordinal());
//			mDialFragment.changeTitleViews(DialFragment.CALLLOG_STATUS);
//		} else {
//			super.onBackPressed();
//		}
//	}

	// 添加标签页 Fragment
	private void addTabs() {
		mTabList = new ArrayList<Fragment>();

		mCallsFragment = new CallsFragment();
		mDiscoverFragment = new DiscoverFragment();
		mMeFragment = new MeFragment();

		mTabList.add(mCallsFragment);
		mTabList.add(mDiscoverFragment);
		mTabList.add(mMeFragment);
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
			case R.id.main_tab_calls_ll: // 普通状态 拨号盘Tab
				setCurrentTab(CALLS_POSITION);
				break;
			case R.id.main_tab_discover_ll:
				setCurrentTab(DISCOVER_POSITION);
				break;
			case R.id.main_tab_me_ll:
				setCurrentTab(ME_POSITION);
				break;
		}
	}


	// 设置标签
	private void setCurrentTab(int operate) {
		boolean needChange = false;
		switch (operate) {
		case CALLS_POSITION:
			if (operate != currentIndex) { // 取消先前选中的tag
				setBottomTabView(CALLS_POSITION);
				needChange = true;
			}
			break;
		case DISCOVER_POSITION:
			if (operate != currentIndex) {
				needChange = true;
				setBottomTabView(DISCOVER_POSITION);
			}
			break;
		case ME_POSITION:
			if (operate != currentIndex) {
				needChange = true;
				setBottomTabView(ME_POSITION);
			}
			break;
		}
		if (needChange) {
			currentIndex = operate;
			main_tab_viewpager.setCurrentItem(currentIndex, false);
		}
	}

	private void setBottomTabView(int index) {
		switch (index) {
		case CALLS_POSITION:
			main_tab_calls_iv.setBackgroundResource(R.drawable.tab_calls_sel_icon);
			main_tab_calls_tv.setTextColor(getResources().getColor(R.color.tab_font_sel_color));

			main_tab_discover_iv.setBackgroundResource(R.drawable.tab_discover_nor_icon);
			main_tab_discover_tv.setTextColor(getResources().getColor(R.color.tab_font_nor_color));

			main_tab_me_iv.setBackgroundResource(R.drawable.tab_me_nor_icon);
			main_tab_me_tv.setTextColor(getResources().getColor(R.color.tab_font_nor_color));
			break;
		case DISCOVER_POSITION:
			main_tab_calls_iv.setBackgroundResource(R.drawable.tab_calls_nor_icon);
			main_tab_calls_tv.setTextColor(getResources().getColor(R.color.tab_font_nor_color));

			main_tab_discover_iv.setBackgroundResource(R.drawable.tab_discover_sel_icon);
			main_tab_discover_tv.setTextColor(getResources().getColor(R.color.tab_font_sel_color));

			main_tab_me_iv.setBackgroundResource(R.drawable.tab_me_nor_icon);
			main_tab_me_tv.setTextColor(getResources().getColor(R.color.tab_font_nor_color));
			break;
		case ME_POSITION:
			main_tab_calls_iv.setBackgroundResource(R.drawable.tab_calls_nor_icon);
			main_tab_calls_tv.setTextColor(getResources().getColor(R.color.tab_font_nor_color));

			main_tab_discover_iv.setBackgroundResource(R.drawable.tab_discover_nor_icon);
			main_tab_discover_tv.setTextColor(getResources().getColor(R.color.tab_font_nor_color));

			main_tab_me_iv.setBackgroundResource(R.drawable.tab_me_sel_icon);
			main_tab_me_tv.setTextColor(getResources().getColor(R.color.tab_font_sel_color));
			break;
		}

	}

	// 测量view的大小
	private void measureView(View view) {

		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (lp == null) {
			lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int widthMeasureSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, lp.width);
		int lpHeight = lp.height;
		int heightMeasureSpec;
		if (lpHeight > 0) {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}

		view.measure(widthMeasureSpec, heightMeasureSpec);
	}

	private void showMeTagNew() {
//		// 我的位置显示小红点
//		String uid = UserData.getInstance().getUserId();
//		boolean needShowActivityNew = Preferences.getBooleanData(mContext, "activity_new_point" + uid, false);
//		boolean accountNew = Preferences.getBooleanData(mContext, "account_new_point" + uid, false);
//
//		if (needShowActivityNew || accountNew) {
//			meNewIV.setVisibility(View.VISIBLE);
//		} else {
//			meNewIV.setVisibility(View.GONE);
//		}
	}

//	public void onEventMainThread(UpdateEvent event) {
//		if (event == null) {
//			return;
//		}
//		if (event.type == UpdateEvent.TYPE_AUTO_UPDATE) {
//			if (event.result == UpdateEvent.NEW_VERSION_YES) {
//				GlobalVariable.showUpdateDialog(this, event.updateInfo);
//			}
//		}
//	}

	/**
	 * 为Fragment提供触摸监听相关
	 * 
	 * @author Evan 2015-9-1
	 *
	 */
	public interface MyTouchListener {
		public void onTouchEvent(MotionEvent event);
	}

	/*
	 * 保存MyTouchListener接口的列表
	 */
	private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MyTouchListener>();

	/**
	 * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
	 * 
	 * @param listener
	 */
	public void registerMyTouchListener(MyTouchListener listener) {
		myTouchListeners.add(listener);
	}

	/**
	 * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
	 * 
	 * @param listener
	 */
	public void unRegisterMyTouchListener(MyTouchListener listener) {
		myTouchListeners.remove(listener);
	}

	/**
	 * 分发触摸事件给所有注册了MyTouchListener的接口
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			for (MyTouchListener listener : myTouchListeners) {
				listener.onTouchEvent(ev);
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	private void handleKeyboard(boolean isOpen) {
		Message msg = Message.obtain();
		msg.what = MessageType.DIAL_KEYBOARD_STATE.ordinal();
		msg.arg1 = isOpen ? View.VISIBLE : View.GONE;
		EventBus.getDefault().post(msg);
	}
}
