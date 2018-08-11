package com.icall.free.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ICallViewPager extends ViewPager {

	private boolean isScroll = false;

	public ICallViewPager(Context context) {
		super(context);
	}

	public ICallViewPager(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void setScroll(boolean flag) {
		this.isScroll = flag;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (isScroll) {
			return super.onTouchEvent(arg0);
		} else {
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isScroll) {
			return super.onTouchEvent(arg0);
		} else {
			return false;
		}
	}

}
