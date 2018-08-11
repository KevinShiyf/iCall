package com.icall.free.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.provider.CallLog;

import com.android.pc.ioc.event.EventBus;
import com.icall.free.entity.CallLogEntity;
import com.icall.free.event.MessageType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author wanghao 2015-4-13 通话记录相关操作
 *
 */
public class CallLogUtil {
	// 系统通话记录查询项
	private static final String[] SYSTEM_CALL_PROJECTION = {
			CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER,
			CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.TYPE };


	public static Map<String, ArrayList<CallLogEntity>> mAllCallsMaps = new LinkedHashMap<String, ArrayList<CallLogEntity>>();
	public static ArrayList<CallLogEntity> mCallLogsList = new ArrayList<CallLogEntity>();
	public static ArrayList<CallLogEntity> mSearchCallsList = new ArrayList<CallLogEntity>();

	// 2015-6-4 加载系统所有通话记录

	public static void loadAllSystemCallLogs(final Context context) {
		if (context == null) {
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				loadSystemCalllogs(context);
			}
		}).start();
	}

	private synchronized static void loadSystemCalllogs(Context context) {
		mAllCallsMaps.clear();
		mCallLogsList.clear();

		Uri uri = CallLog.Calls.CONTENT_URI;
		ContentResolver cr = context.getContentResolver();
		String selection = null;
		String[] selectionArgs = null;
		Cursor cursor = null;
		try {
			cursor = cr.query(uri, SYSTEM_CALL_PROJECTION, selection, selectionArgs, CallLog.Calls.DATE + " desc");
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		if (cursor == null)
			return;
		try {
			int count = cursor.getCount();
			cursor.moveToFirst();
			for (int i = 0; i < count; i++) {
				cursor.moveToPosition(i);

				String name = cursor.getString(cursor.getColumnIndex(SYSTEM_CALL_PROJECTION[0]));
				String number = cursor.getString(cursor.getColumnIndex(SYSTEM_CALL_PROJECTION[1]));
				long date = cursor.getLong(cursor.getColumnIndex(SYSTEM_CALL_PROJECTION[2]));
				long duration = cursor.getLong(cursor.getColumnIndex(SYSTEM_CALL_PROJECTION[3]));
				int type = cursor.getInt(cursor.getColumnIndex(SYSTEM_CALL_PROJECTION[4]));

				if (number == null || number.length() < 3) {
					continue;
				}
				if (number.contains("-")) {
					number = number.replaceAll("-", ""); // 去除号码中包含的'-'字符
				}
				if (number.contains(" ")) { // 去掉号码中包含的' '字符
					number = number.replaceAll(" ", "");
				}
				CallLogEntity item = new CallLogEntity();
				item.setPhone(number);
				item.setType(type);
				item.setDate(date);
				item.setDuration(duration);

				if (mAllCallsMaps.containsKey(number)) {
					ArrayList<CallLogEntity> list = mAllCallsMaps.get(number);
					list.add(item);
				} else {
					ArrayList<CallLogEntity> list = new ArrayList<CallLogEntity>();
					list.add(item);
					mAllCallsMaps.put(number, list);
				}
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//2015年12月17日17:19:10
		//需要重新在联系人数据库是读取名字,部分手机联系人名字变更后通话记录数据库名字未同步
		//最多读取50条通话记录数据，数据量大会影响速度
		for (String phone : mAllCallsMaps.keySet()) {
			ArrayList<CallLogEntity> list = mAllCallsMaps.get(phone);
			if (list != null && list.size() > 0) {
				mCallLogsList.add(list.get(0));
			}
			if (mCallLogsList.size() >= 50) {
				break;
			}
		}
		for (int i = 0; i < mCallLogsList.size(); i++) {
			CallLogEntity item = mCallLogsList.get(i);
			String phoneNum = item.getPhone();
			String name = ContactsUtil.getNameByPhone(context, phoneNum);
			item.setName(name);
		}
		sendComplteLoadMessge();
	}


	// 删除通话记录 2015-6-4
	public static void deleteCalls(Context context, String number) {
		if (number != null) { // 删除单条通话记录
			String[] selectionArgs = { number };
			String systemLocation = CallLog.Calls.NUMBER + " =? ";
			context.getContentResolver().delete(CallLog.Calls.CONTENT_URI,
					systemLocation, selectionArgs);
		} else {
			// 删除系统本地通话记录
			context.getContentResolver().delete(CallLog.Calls.CONTENT_URI,
					null, null);
		}
	}
	
	//添加通话记录 2015-6-5
	public static void insertCalls(Context context, ContentValues cv) {
		context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, cv);
	}

	// 发送加载完成消息
	private static void sendComplteLoadMessge() {
		Message msg = new Message();
		msg.what = MessageType.COMPLETE_LOAD_CALLLOGS.ordinal();
		EventBus.getDefault().post(msg);
	}

	// 获取搜索的通话记录
	public static void getSearchCallLogs(String str) {
		mSearchCallsList.clear();
		try {
			for (CallLogEntity item : mCallLogsList) {
				if (item.getPhone().contains(str)) {
					String name = item.getName();
					if (name == null || name.length() == 0) {
						mSearchCallsList.add(item);
					} else {
						if (!ContactsUtil.judgeExistByName(name)) {
							mSearchCallsList.add(item);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
