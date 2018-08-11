package com.icall.free.util;

import android.os.Handler;
import android.text.TextUtils;

import com.icall.free.ICallApplication;

import java.io.IOException;
import java.util.HashMap;

public class CallLogsNameSyncloader {
	private Object lock = new Object();
	private boolean mAllowLoad = true;
	private boolean firstLoad = true;
	private int mStartLoadLimit = 0;
	private int mStopLoadLimit = 0;
	final Handler handler = new Handler();
	private HashMap<String, String> nameCache = new HashMap<String, String>();
	
	private static CallLogsNameSyncloader instance;
	
	private CallLogsNameSyncloader() {
	}

	public static CallLogsNameSyncloader getInstance() {
		if (instance == null) {
			synchronized (CallLogsNameSyncloader.class) {
				if (instance == null) {
					instance = new CallLogsNameSyncloader();
				}
			}
		}
		return instance;
	}
	
	public interface OnNameLoadListener {
		public void onNameLoad(String name, String phone, int position);
		public void onError(Integer t);
	}

	public void setLoadLimit(int startLoadLimit, int stopLoadLimit) {
		if (startLoadLimit > stopLoadLimit) {
			return;
		}
		mStartLoadLimit = startLoadLimit;
		mStopLoadLimit = stopLoadLimit;
	}

	public void restore() {
		mAllowLoad = true;
		firstLoad = true;
	}

	public void lock() {
		mAllowLoad = false;
		firstLoad = false;
	}

	public void unlock() {
		mAllowLoad = true;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public String getCacheName(String phone) {
		String key = phone;
		if (nameCache.containsKey(key)) {
			String softReference = nameCache.get(key);
			return softReference;
		}
		return null;
	}
	
	public void clearCacheName() {
		nameCache.clear();
	}
	
	public void loadImage(Integer t, String phone, OnNameLoadListener listener) {
		final OnNameLoadListener mListener = listener;
		final Integer mt = t;
		final String mPhone = phone;
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (!mAllowLoad) {
					synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				if (mAllowLoad && firstLoad && mListener != null) {
					loadImage(mPhone, mt, mListener);
				}

				if (mAllowLoad && mt <= mStopLoadLimit && mt >= mStartLoadLimit&&mListener!=null) {
					loadImage(mPhone, mt, mListener);
				}
			}

		}).start();
	}

	private void loadImage(final String phone, final Integer mt, final OnNameLoadListener mListener) {
		String key = phone;
		try {
			final String d = loadName(phone);
			if (!TextUtils.isEmpty(d)) {
				nameCache.put(key, d);
			} else {
				nameCache.remove(key);
			}
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (mAllowLoad && mListener != null) {
						mListener.onNameLoad(d, phone, mt);
					}
				}
			});
		} catch (IOException e) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (mListener != null) {
						mListener.onError(mt);
					}
				}
			});
			e.printStackTrace();
		}
	}

	private String loadName(String phone) throws IOException {
		String data = ContactsUtil.getNameByPhone(ICallApplication.getApplication(), phone);
		return data;
	}
}
