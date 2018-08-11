package com.icall.free.activity.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.pc.ioc.event.EventBus;
import com.android.pc.ioc.inject.InjectAll;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.util.Handler_Inject;
import com.icall.free.R;
import com.icall.free.adapter.RecentsAdapter;
import com.icall.free.entity.CallLogEntity;
import com.icall.free.event.MessageType;
import com.icall.free.util.CallLogUtil;
import com.icall.free.util.CallLogsNameSyncloader;
import com.icall.free.util.CallLogsNameSyncloader.OnNameLoadListener;
import com.icall.free.util.StringUtil;

import java.util.ArrayList;

public class RecentsFragment extends BaseFragment  {
    @InjectAll
    Views views;

    class Views{
        public ListView recents_listview;
    }

    private RecentsAdapter recentsAdapter;

    private ArrayList<CallLogEntity> mCallLogsList = new ArrayList<CallLogEntity>();

    private ArrayList<CallLogEntity> mSearchCallLogList = new ArrayList<CallLogEntity>();

    private String area = "";
    private CallLogsNameSyncloader syncLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.recents_activity, container, false);
        Handler_Inject.injectOrther(this, rootView);
        return rootView;
    }

    @InjectInit
    private void init() {
        mCallLogsList.clear();
        mCallLogsList.addAll(CallLogUtil.mCallLogsList);
        syncLoader = CallLogsNameSyncloader.getInstance();
        recentsAdapter = new RecentsAdapter(getActivity(), mCallLogsList, nameLoadListener);
//        recentsAdapter = new RecentsAdapter(this.getFragmentManager());
        views.recents_listview.setAdapter(recentsAdapter);
        views.recents_listview.setOnScrollListener(scrollListener);
    }


    public void loadName() {
        int start = views.recents_listview.getFirstVisiblePosition();
        int end = views.recents_listview.getLastVisiblePosition();
        if (end >= views.recents_listview.getCount()) {
            end = views.recents_listview.getCount() - 1;
        }
        syncLoader.setLoadLimit(start, end);
        syncLoader.unlock();
    }


    private OnNameLoadListener nameLoadListener = new OnNameLoadListener() {
        @Override
        public void onNameLoad(String name, String phone, int pos) {
            TextView nameView = (TextView) views.recents_listview.findViewWithTag(phone + "up");
            if (nameView != null && StringUtil.isNotNull(name)) {
                nameView.setText(name);
            }
        }

        @Override
        public void onError(Integer t) {

        }
    };

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView arg0, int arg1) {
            // 隐藏拨号盘
            Message msg = new Message();
            msg.what = MessageType.SCROOL_HIDE_DIAL_MESSAGE.ordinal();
            EventBus.getDefault().post(msg);

            switch (arg1) {
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    syncLoader.lock();
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    loadName();
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    syncLoader.lock();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

        }
    };
}
