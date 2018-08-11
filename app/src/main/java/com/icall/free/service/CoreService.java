package com.icall.free.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;

import com.android.pc.ioc.event.EventBus;
import com.icall.free.event.MessageType;
import com.icall.free.util.CallLogUtil;
import com.icall.free.util.CallLogsNameSyncloader;
import com.icall.free.util.DfineAction;
import com.icall.free.util.ILog;
/**
 * @author: xiaozhenhua
 * @data: 2018-01-25
 */

public class CoreService extends Service {
    /**
     * @since 2015-11-12
     * @author wanghao 日志文件的目录
     */
    private static final int HANDLE_MSG_CALLS_LOG_CHANGED = 0;
    private static final int HANDLE_MSG_CONTACTS_CHANGED = 1;

    private Context mContext = null;

    private CustomPhoneStateListener phoneStateListener = null;
    public void onCreate() {
        super.onCreate();
        mContext = this;
        loadData();
        registerBroadcast();
        registerContactObserver();
        registerCallLogsObserver();
//
        initPhoneLib();
//        String uid = UserData.getInstance().getUserId();
//        String password = UserData.getInstance().getPassword();
//
//        if (!StringUtil.isNull(uid) && !StringUtil.isNull(password)) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(3000);
//                        Intent intent = new Intent(DfineAction.ACTION_CONNECT_YTX);
//                        sendBroadcast(intent);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
    }

    private void initPhoneLib() {

    }

    private void destroyPhoneLib() {

    }

    private void registerPhoneStateListener() {
        phoneStateListener = new CustomPhoneStateListener();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    private void removePhoneStateListener() {
        if (phoneStateListener != null) {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyPhoneLib();
        unregisterBroadcast();
        unregisterContactObserver();
        unregisterCallLogsObserver();
    }


    // 加载数据
    private void loadData() {
        try {
            CallLogUtil.loadAllSystemCallLogs(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @since 2015-8-28
     * @author wanghao 注册广播
     */
    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DfineAction.ACTION_INIT_YTX_SDK);
        filter.addAction(DfineAction.ACTION_CONNECT_YTX);
        filter.addAction(DfineAction.ACTION_DIAL_PHONE);
        filter.addAction(DfineAction.ACTION_HANGUP_PHONE);
        filter.addAction(DfineAction.ACTION_INCOMING_ANSWER);
        filter.addAction(DfineAction.ACTION_SYSTEM_PHONE);
        this.registerReceiver(mReceiver, filter);
    }

    /**
     * @since 2015-8-28
     * @author wanghao 注销广播
     */
    private void unregisterBroadcast() {
        if (mReceiver != null) {
            this.unregisterReceiver(mReceiver);
        }
    }

    /**
     * @since 2015-8-28
     * @author wanghao 定义广播
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getAction().equals(DfineAction.ACTION_INIT_YTX_SDK)) {
				initPhoneLib();
            } else if (arg1.getAction().equals(DfineAction.ACTION_SYSTEM_PHONE)) {
                registerPhoneStateListener();
            }
        }
    };

    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_MSG_CALLS_LOG_CHANGED:
                    CallLogUtil.loadAllSystemCallLogs(mContext);
                    break;
                case HANDLE_MSG_CONTACTS_CHANGED: {
                    CallLogsNameSyncloader.getInstance().clearCacheName();
                    Message eventMsg = Message.obtain();
                    eventMsg.what = MessageType.LOCAL_CONTACTS_CHANGE.ordinal();
                    EventBus.getDefault().post(eventMsg);
                }
                break;
            }
        }
    };

    private ContentObserver mCallLogsObservable = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            mHandle.removeMessages(HANDLE_MSG_CALLS_LOG_CHANGED);
            mHandle.sendEmptyMessageDelayed(HANDLE_MSG_CALLS_LOG_CHANGED, 500);
        }
    };

    // 注册ContentObserverR
    private void registerCallLogsObserver() {
        getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, false,
                mCallLogsObservable); // 注册系统通话记录
    }

    private void unregisterCallLogsObserver() {
        getContentResolver().unregisterContentObserver(mCallLogsObservable); // 注册系统通话记录
    }

    /**
     * 观察本地通讯录的变化
     */
    private ContentObserver mContactObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            ILog.i("cool_test", "联系人变化----------------- selfChange = " + selfChange);
            mHandle.removeMessages(HANDLE_MSG_CONTACTS_CHANGED);
            mHandle.sendEmptyMessageDelayed(HANDLE_MSG_CONTACTS_CHANGED, 500);

        }
    };

    private void registerContactObserver(){
        Uri uri = Uri.parse("content://com.android.contacts/data/phones");
        getContentResolver().registerContentObserver(uri, true, mContactObserver);
    }

    private void unregisterContactObserver(){
        getContentResolver().unregisterContentObserver(mContactObserver);
    }


    public class CustomPhoneStateListener extends PhoneStateListener {
        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            super.onServiceStateChanged(serviceState);
            ILog.d("CustomPhoneStateListener onServiceStateChanged: " + serviceState);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            ILog.d("CustomPhoneStateListener state: "
                    + state + " incomingNumber: " + incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:      // 电话挂断
                    break;
                case TelephonyManager.CALL_STATE_RINGING:   // 电话响铃
                    ILog.d("CustomPhoneStateListener onCallStateChanged endCall");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:   // 来电接通 或者 去电，去电接通  但是没法区分
                    break;
            }
        }
    }
}